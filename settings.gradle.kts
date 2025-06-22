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

rootProject.name = "Ember"
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
    ":feature-simulation",
    ":feature-training",
)
