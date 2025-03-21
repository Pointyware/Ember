plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvmToolchain(17)
    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.coreEntities)

                api(libs.ktor.clientCore)
                api(libs.ktor.clientOkhttp)
                api(libs.ktor.clientLogging)
                api(libs.ktor.clientResources)
                api(libs.ktor.clientContentNegotiation)
                api(libs.ktor.serializationKotlinxJson)
            }
        }
        val desktopMain by getting {
            dependencies {
//                implementation(libs.ktor)
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
