plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    jvmToolchain(17)
    androidTarget() {

    }
}

android {
    namespace = "org.pointyware.artes"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}
