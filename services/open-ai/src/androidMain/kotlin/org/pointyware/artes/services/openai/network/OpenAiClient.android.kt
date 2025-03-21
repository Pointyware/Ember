package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import org.pointyware.artes.core.remote.defaultHttpClient

/**
 *
 */
actual fun openAiHttpClient(): HttpClient {
    return defaultHttpClient(baseUrl = OPENAI_BASE_URL)
}
