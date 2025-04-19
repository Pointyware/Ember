
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxKover)
}

kotlin {
    jvmToolchain(17)
    jvm("desktop") {
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.appShared)

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

compose.desktop {
    application {
        mainClass = "org.pointyware.artes.ApplicationKt"
    }

}
