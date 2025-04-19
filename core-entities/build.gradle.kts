plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxKover)
}

kotlin {
    jvmToolchain(17)
    jvm {
    }
    androidTarget {
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.pointyware.artes.entities"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}
