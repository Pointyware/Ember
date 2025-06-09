enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Artes"
include(
    ":app-android",
    ":app-desktop",
//    ":app-ios",
    ":app-shared"
)

include(
    ":core-common",
    ":core-data",
    ":core-ui",
    ":core-interactors",
    ":core-viewmodels",
    ":core-entities",
)

include(
    ":feature-agents",
    ":feature-text-chat",
)

include(
    ":services:open-ai"
)
