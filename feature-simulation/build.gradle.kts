import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotlinxKover)
}

kotlin {

    jvmToolchain(21)
    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.coreCommon)
                implementation(projects.coreEntities)
                implementation(projects.coreUi)

                api(compose.ui)
                api(compose.foundation)
                api(compose.material3)
                api(compose.components.resources)
                api(libs.compose.navigation)

                implementation(compose.material3AdaptiveNavigationSuite)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)

                implementation(libs.kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutinesTest)

                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.uiToolingPreview)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "org.pointyware.ember.simulation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(libs.androidx.uiTooling)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.ember.simulation"
    generateResClass = always
}
