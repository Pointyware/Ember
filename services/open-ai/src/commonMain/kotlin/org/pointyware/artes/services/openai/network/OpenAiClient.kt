package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient


const val OPENAI_BASE_URL = "https://api.openai.com/v1/"

/**
 * Creates a ktor [HttpClient] that connects to OpenAI with default platform configuration.
 */
expect fun openAiHttpClient(): HttpClient
