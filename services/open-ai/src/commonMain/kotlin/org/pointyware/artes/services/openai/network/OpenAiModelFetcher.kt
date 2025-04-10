package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import org.pointyware.artes.services.openai.OpenAiConfig

/**
 *
 */
class OpenAiModelFetcher(
    private val client: HttpClient
) {
    suspend fun invoke(config: OpenAiConfig): List<ModelDto> {
        val response = client.get(Models) {
            header("Authorization", "Bearer ${config.apiKey}")
            header("OpenAI-Organization", config.orgId)

            url("http://coreapi-api.unified-35.api.openai.com/v1/models")
        }
        if (response.status.isSuccess()) {
            return response.body<ModelsResponse>().data
        } else {
            throw Exception("Error fetching models: $response")
        }
    }
}
