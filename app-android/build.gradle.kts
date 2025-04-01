plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    jvmToolchain(17)
    androidTarget() {
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(projects.appShared)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)
                implementation(libs.koin.android)
            }
        }
    }
}

android {
    namespace = "org.pointyware.artes"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activityCompose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.compose.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
