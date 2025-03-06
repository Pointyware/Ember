plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    jvm("desktop")
    androidTarget() {

    }
}

android {
    namespace = "org.pointyware.artes.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}
