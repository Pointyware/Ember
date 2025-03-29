plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
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

                implementation(libs.sqlDelight.runtime)
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
                implementation(libs.sqlDelight.jvm)
            }
        }
        val androidMain by getting {
            dependencies {
//                implementation(libs.ktor.clientAndroid)
                implementation(libs.sqlDelight.android)
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

sqldelight {
    databases {
        create("HostDb") {
            packageName = "org.pointyware.artes.data.hosts.db"
        }
    }
}
