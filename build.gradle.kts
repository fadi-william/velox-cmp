plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.storytale) apply false
}

group = "com.levioza.velox"
version = "0.1.0-SNAPSHOT"

tasks.register("docs") {
    group = "documentation"
    description = "Generates the Velox API reference with Dokka."
    dependsOn(":design-system:velox:dokkaGenerate")
}

// Storytale 0.0.5-dev24 resolves this Android resource task from the root project.
// Keep this bridge until the plugin uses the catalog module's task path directly.
tasks.register("generateResourceAccessorsForCommonStories") {
    dependsOn(":catalog:storytale:generateResourceAccessorsForCommonStories")
}
