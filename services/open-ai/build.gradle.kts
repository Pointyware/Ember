plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvmToolchain(17)

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.coreData)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutinesTest)
            }
        }
    }
}
