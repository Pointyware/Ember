package org.pointyware.artes.services.openai.network.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.artes.services.openai.network.OpenAiCredentials
import org.pointyware.artes.services.openai.network.OpenAiModelFetcher

/**
 */
expect fun platformDefaultOpenAiCredentials(): OpenAiCredentials

fun openAiModule() = module {
    single<OpenAiCredentials> {
        platformDefaultOpenAiCredentials()
    }

    factoryOf(::OpenAiModelFetcher)

//    singleOf(::platformDefaultOpenAiCredentials) {
//        bind<OpenAiCredentials>()
//    }
}
