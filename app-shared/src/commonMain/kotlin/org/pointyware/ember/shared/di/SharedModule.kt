package org.pointyware.ember.shared.di

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.artes.data.defaultHttpClient
import org.pointyware.artes.data.di.dataModule
import org.pointyware.artes.interactors.di.interactorsModule
import org.pointyware.artes.viewmodels.di.viewModelModule
import org.pointyware.ember.training.di.trainingModule

/**
 */
val appQualifier = named("application")

expect fun platformSharedModule(): Module

fun sharedModule() = module {

    includes(
        platformSharedModule(),

        dataModule(),
        sharedDataModule(),
        viewModelModule(),
        sharedViewModelModule(),
        interactorsModule(),
        sharedInteractorsModule(),

        trainingModule(),
    )

    single<HttpClient> { defaultHttpClient(baseUrl = "") }
}

fun sharedViewModelModule() = module {
}

fun sharedInteractorsModule() = module {
}

fun sharedDataModule() = module {
}
