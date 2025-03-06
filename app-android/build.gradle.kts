plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    androidTarget() {

    }
}

android {
    namespace = "org.pointyware.artes"
}
