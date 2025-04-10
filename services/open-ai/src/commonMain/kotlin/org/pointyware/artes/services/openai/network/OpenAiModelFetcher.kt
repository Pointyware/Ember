package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.header
import org.pointyware.artes.services.openai.OpenAiConfig

/**
 *
 */
class OpenAiModelFetcher(
    private val client: HttpClient
) {
    suspend fun invoke(config: OpenAiConfig): List<ModelDto> {
        client.get(Models) {
            header("Authorization", "Bearer ${config.apiKey}")
            header("OpenAI-Organization", config.orgId)
        }.body<ModelsResponse>().let {
            return it.data
        }
    }
}
