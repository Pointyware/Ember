import com.android.build.gradle.internal.ide.kmp.KotlinAndroidSourceSetMarker.Companion.android

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    jvm("desktop")
    androidTarget() {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.compose.navigation)

                implementation(compose.material3AdaptiveNavigationSuite)

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
    namespace = "org.pointyware.artes.ui"
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
