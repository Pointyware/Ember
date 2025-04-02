package org.pointyware.artes.shared.di

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.artes.data.di.dataModule
import org.pointyware.artes.data.di.dataQualifier
import org.pointyware.artes.hosts.interactors.CreateHostUseCase
import org.pointyware.artes.hosts.viewmodels.HostViewModel
import org.pointyware.artes.services.openai.network.di.openAiModule
import org.pointyware.artes.services.openai.network.openAiHttpClient
import org.pointyware.artes.text.completion.CompletionViewModel

/**
 */
val appQualifier = named("application")

expect fun platformSharedModule(): Module

fun sharedModule() = module {

    includes(
        platformSharedModule(),

        dataModule(),

        sharedViewModelModule(),
        sharedInteractorsModule(),

        openAiModule()
    )

    single<HttpClient> { openAiHttpClient() }
}

fun sharedViewModelModule() = module {
    single<CompletionViewModel> {
        CompletionViewModel(
            get(),
            get(qualifier = dataQualifier),
            get(qualifier = dataQualifier)
        )
    }

    single<HostViewModel> {
        HostViewModel(
            get(),
        )
    }
}

fun sharedInteractorsModule() = module {
    single<CreateHostUseCase> {
        CreateHostUseCase(get())
    }
}
