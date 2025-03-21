
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.composeCompiler)
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
