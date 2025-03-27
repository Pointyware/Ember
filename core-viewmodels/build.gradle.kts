import com.android.build.gradle.internal.ide.kmp.KotlinAndroidSourceSetMarker.Companion.android

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    jvmToolchain(17)
    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.coreEntities)
                implementation(projects.coreData)
                implementation(projects.coreInteractors)

                implementation(libs.kotlinx.coroutinesCore)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)

                implementation(compose.runtime)
                api(libs.compose.viewModels)
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
    namespace = "org.pointyware.artes.viewmodels"
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
