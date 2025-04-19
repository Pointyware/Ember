package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
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

            expectSuccess = false
        }
        return tryAndValidate(response, config, 100)
    }

    private suspend fun tryAndValidate(response: HttpResponse, config: OpenAiConfig, retryDelay: Long): List<ModelDto> {
        return if (response.status.isSuccess()) {
            return response.body<ModelsResponse>().data
        } else if (response.status.value in 300..399) {
            val location = response.headers["Location"] ?: throw Exception("No location header")
            val redirectResponse = client.get(location) {
                header("Authorization", "Bearer ${config.apiKey}")
                header("OpenAI-Organization", config.orgId)

                expectSuccess = false
            }
            tryAndValidate(redirectResponse, config, retryDelay * 10)
        } else {
            throw Exception("Error fetching models: $response - \n ${response.body<String>()}")
        }
    }
}
