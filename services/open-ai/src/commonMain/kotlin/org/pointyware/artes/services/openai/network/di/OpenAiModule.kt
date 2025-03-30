package org.pointyware.artes.services.openai.network.di

import org.koin.dsl.module
import org.pointyware.artes.services.openai.network.OpenAiCredentials

/**
 */
expect fun platformDefaultOpenAiCredentials(): OpenAiCredentials

fun openAiModule() = module {
    single<OpenAiCredentials> {
        platformDefaultOpenAiCredentials()
    }

//    singleOf(::platformDefaultOpenAiCredentials) {
//        bind<OpenAiCredentials>()
//    }
}
