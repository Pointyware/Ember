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

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)

                api(libs.ktor.clientCore)
                api(libs.ktor.clientOkhttp)
                api(libs.ktor.clientLogging)
                api(libs.ktor.clientResources)
                api(libs.ktor.clientContentNegotiation)
                api(libs.ktor.serializationKotlinxJson)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutinesTest)
                implementation(libs.truth)
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
