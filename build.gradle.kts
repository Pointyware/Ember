plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinAndroid) apply false

    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.composePlugin) apply false
    alias(libs.plugins.composeCompiler) apply false

    alias(libs.plugins.sqlDelight) apply false
}
