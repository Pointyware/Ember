package org.pointyware.artes.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging

/**
 * Provides a client configured for generic use
 */
fun defaultHttpClient(baseUrl: String?): HttpClient = HttpClient {
    expectSuccess = true
    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    defaultRequest {
        baseUrl?.let { url(it) }
        contentType(ContentType.Application.Json)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.HEADERS
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}
