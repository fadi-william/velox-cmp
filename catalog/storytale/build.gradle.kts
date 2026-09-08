import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.storytale)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
}

configurations.configureEach {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.jetbrains.compose.storytale:gallery"))
            .using(project(":tooling:storytale-gallery"))
            .because("Velox carries a small Storytale gallery style override")
    }
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    wasmJs {
        outputModuleName.set("velox-catalog")
        browser {
            commonWebpackConfig {
                // Storytale generates the gallery page with a hard-coded <script src="composeApp.js">,
                // so the bundle has to keep that name or the page loads nothing.
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }

    jvm("desktop")

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "VeloxCatalog"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":design-system:velox"))
            implementation(project(":design-system:velox-forms"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(compose.preview)
        }
        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.levioza.velox.catalog"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.levioza.velox.catalog"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures.compose = true

    buildTypes.named("Stories") {
        matchingFallbacks += listOf("debug", "release")
    }

    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.levioza.velox.catalog.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "VeloxCatalog"
            packageVersion = "1.0.0"
        }
    }
}


// Work around Storytale selecting an obsolete iPhone device type for recent iOS runtimes.
val ensureStorytaleIosSimulator by tasks.registering {
    group = "Storytale"
    description = "Ensures Storytale has a simulator compatible with an installed iOS runtime"

    doLast {
        val simulatorName = "Storytale iOS Device"
        val availableDevices = providers.exec {
            commandLine("/usr/bin/xcrun", "simctl", "list", "devices", "available")
        }.standardOutput.asText.get()

        if (!availableDevices.contains(simulatorName)) {
            val sourceDeviceId = Regex(
                pattern = """(?m)^\s+iPhone .+ \(([0-9A-Fa-f-]{36})\) \((?:Booted|Shutdown)\)""",
            ).find(availableDevices)?.groupValues?.get(1)
                ?: error("No available iPhone simulator was found. Install an iOS simulator runtime in Xcode.")

            providers.exec {
                commandLine("/usr/bin/xcrun", "simctl", "clone", sourceDeviceId, simulatorName)
            }.result.get().assertNormalExitValue()
        }
    }
}

tasks.named("storytaleRegisterIosSimulatorArm64") {
    dependsOn(ensureStorytaleIosSimulator)
}


// Storytale's generated SwiftUI host constrains Compose to the safe area by default.
// Let Compose paint edge-to-edge; the gallery shell applies WindowInsets.safeDrawing to its content.
val patchStorytaleIosSafeArea by tasks.registering {
    group = "Storytale"
    description = "Makes the generated Storytale iOS host edge-to-edge"

    dependsOn("storytaleUnzipXCodeProject")

    doLast {
        val contentView = layout.buildDirectory.file(
            "storytale/StorytaleXCode/StorytaleXCode/StorytaleXCode/ContentView.swift",
        ).get().asFile
        val generatedInset = ".ignoresSafeArea(.keyboard) // Compose has own keyboard handler"
        val composeManagedInset = ".ignoresSafeArea() // Compose applies safe drawing and keyboard insets"

        val source = contentView.readText()
        if (!source.contains(composeManagedInset)) {
            check(source.contains(generatedInset)) {
                "Storytale's generated ContentView.swift has changed; update the safe-area patch."
            }
            contentView.writeText(source.replace(generatedInset, composeManagedInset))
        }
    }
}

tasks.named("storytaleBuildIosSimulatorArm64") {
    dependsOn(patchStorytaleIosSafeArea)
}


// Compose 1.10 requires this plist entry; Storytale upstream does not add it yet.
tasks.named("storytaleInstallAppIosSimulatorArm64") {
    doFirst {
        val appBundle = layout.buildDirectory.dir(
            "storytale/StorytaleXCode/dd/Build/Products/Debug-iphonesimulator/StorytaleXCode.app",
        ).get().asFile
        val infoPlist = appBundle.resolve("Info.plist")

        providers.exec {
            commandLine(
                "/usr/bin/plutil",
                "-insert",
                "CADisableMinimumFrameDurationOnPhone",
                "-bool",
                "YES",
                infoPlist.absolutePath,
            )
        }.result.get().assertNormalExitValue()

        providers.exec {
            commandLine("/usr/bin/codesign", "--force", "--sign", "-", appBundle.absolutePath)
        }.result.get().assertNormalExitValue()
    }
}
