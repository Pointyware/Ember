plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxKover)
}

kotlin {
    jvmToolchain(21)
    jvm("desktop") {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        mainRun {
//            mainClass = "org.pointyware.ember.MainKt"
//        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.appShared)

                implementation(compose.ui)
                implementation(compose.material3)

                implementation(libs.koin.core)
                implementation(libs.koin.coroutines)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "org.pointyware.ember.ApplicationKt"
        }
    }
}
