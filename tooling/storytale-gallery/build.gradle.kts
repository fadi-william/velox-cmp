import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    js { browser() }
    wasmJs { browser() }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }
    androidTarget {
        publishLibraryVariants("debug", "release")
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.highlights)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.material.icons.core)
            implementation(libs.material3.adaptive)
            implementation(libs.navigation.compose)
            implementation(libs.storytale.runtime)
        }

        val mobileMain by creating {
            dependsOn(commonMain.get())
        }
        androidMain { dependsOn(mobileMain) }
        iosMain { dependsOn(mobileMain) }

        val desktopMain by creating {
            dependsOn(commonMain.get())
        }
        jsMain { dependsOn(desktopMain) }
        wasmJsMain { dependsOn(desktopMain) }
        jvmMain {
            dependsOn(desktopMain)
            dependencies { implementation(compose.desktop.currentOs) }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-Xexpect-actual-classes",
        )
    }
}

compose.resources {
    packageOfResClass = "org.jetbrains.compose.storytale.gallery.generated.resources"
}

android {
    namespace = "org.jetbrains.compose.storytale.gallery"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

group = "org.jetbrains.compose.storytale"
version = libs.versions.storytale.get()
