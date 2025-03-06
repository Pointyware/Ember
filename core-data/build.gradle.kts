plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {

        }
    }
}

android {
    namespace = "org.pointyware.artes.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}
