plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinAndroid) apply false

    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.composePlugin) apply false
    alias(libs.plugins.composeCompiler) apply false

    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.ksp) apply false

    alias(libs.plugins.kotlinxKover)
}

dependencies {
    kover(projects.coreEntities)
    kover(projects.coreData)
    kover(projects.coreViewmodels)
    kover(projects.coreUi)
    kover(projects.coreData)

    kover(projects.featureTextChat)
    kover(projects.services.openAi)

    kover(projects.appShared)

    kover(projects.appAndroid)
    kover(projects.appDesktop)
}

kover.reports {
    filters {
        excludes.classes("kotlinx.kover.examples.merged.utils.*", "kotlinx.kover.examples.merged.subproject.utils.*")
        includes.classes("kotlinx.kover.examples.merged.*")
    }

    verify {
        rule {
            bound {
                minValue.set(50)
                maxValue.set(75)
            }
        }
    }
}
