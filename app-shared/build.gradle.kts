plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composePlugin)
}

kotlin {

    jvmToolchain(17)
    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.services.openAi)
                api(projects.coreUi)
                api(projects.coreViewmodels)
                api(projects.coreData)

                implementation(compose.ui)
                implementation(compose.material3)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.uiToolingPreview)
            }
        }
    }
}

android {
    namespace = "org.pointyware.artes.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
