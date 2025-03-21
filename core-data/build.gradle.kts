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
            dependencies {
                implementation(libs.ktor.clientContentNegotiation)
                implementation(libs.ktor.clientCore)
                implementation(libs.ktor.clientLogging)
                implementation(libs.ktor.clientOkhttp)
                implementation(libs.ktor.clientResources)
            }
        }
        val androidMain by getting {
            dependencies {
//                implementation(libs.ktor.clientAndroid)
            }
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
