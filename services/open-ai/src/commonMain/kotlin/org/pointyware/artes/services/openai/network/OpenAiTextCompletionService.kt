package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import org.pointyware.artes.data.TextCompletionService

/**
 * Client interface to interact with OpenAI API text completion endpoint.
 */
class OpenAiTextCompletionService(
    private val client: HttpClient,
    private val auth: OpenAiCredentials,
    // TODO: add modelProvider?
): TextCompletionService {

    override suspend fun complete(prompt: String): String {
        client.post(Completions) {
            header("Authorization", "Bearer ${auth.apiKey}")
            header("OpenAI-Organization", auth.orgId)
            setBody(
                Completions.Request(model = "o3-mini", prompt = prompt)
            )
        }.body<Response>().let {
            return it.choices[0].text
        }
    }
}
