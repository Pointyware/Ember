package org.pointyware.artes.shared.di

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel
import org.pointyware.artes.data.di.dataModule
import org.pointyware.artes.data.di.dataQualifier
import org.pointyware.artes.data.hosts.HostDao
import org.pointyware.artes.data.hosts.SqlDelightHostDao
import org.pointyware.artes.hosts.interactors.CreateHostUseCase
import org.pointyware.artes.hosts.viewmodels.HostViewModel
import org.pointyware.artes.interactors.CreateAgentUseCase
import org.pointyware.artes.interactors.GetServiceModelsUseCase
import org.pointyware.artes.interactors.di.interactorsModule
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
        sharedDataModule(),
        interactorsModule(),

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

    factory<AgentEditorViewModel> {
        AgentEditorViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}

fun sharedInteractorsModule() = module {
    factoryOf(::CreateHostUseCase)
    factoryOf(::CreateAgentUseCase)
    factoryOf(::GetServiceModelsUseCase)
}

fun sharedDataModule() = module {
    single<HostDao> {
        SqlDelightHostDao(
            get()
        )
    }
}
