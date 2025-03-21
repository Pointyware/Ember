package org.pointyware.artes.services.openai.network.di

import org.pointyware.artes.services.openai.network.OpenAiCredentials
import org.pointyware.artes.services.openai.network.openAiCredentials

/**
 */
actual fun platformDefaultOpenAiCredentials(): OpenAiCredentials {
    return openAiCredentials().getOrThrow()
}
