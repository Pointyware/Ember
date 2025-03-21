package org.pointyware.artes.shared.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module
import org.pointyware.artes.data.TextCompletionService
import org.pointyware.artes.services.openai.network.OpenAiTextCompletionService

actual fun platformSharedModule(): Module {
    return module {
        single<CoroutineScope>(qualifier = appQualifier) {
            CoroutineScope(SupervisorJob() + Dispatchers.Main)
        }

        single<TextCompletionService> {
            OpenAiTextCompletionService(get(), get())
        }
    }
}
