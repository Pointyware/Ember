plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvmToolchain(17)

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
    }
}
