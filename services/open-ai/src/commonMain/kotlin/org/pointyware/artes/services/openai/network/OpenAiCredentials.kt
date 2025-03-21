package org.pointyware.artes.services.openai.network

/**
 * Contains the authentication information for the OpenAI API.
 *
 * HTTP Headers
 * ```
 * Content-Type: application/json
 * Authorization: Bearer OPENAI_API_KEY
 * OpenAI-Organization: org-jpfKZMASBCzESMQ2IUF7liiN
 * ```
 * organization header is optional
 *
 * @property apiKey The API key for the OpenAI API.
 * @property orgId The organization ID for the OpenAI API.
 */
data class OpenAiCredentials(
    val apiKey: String,
    val orgId: String? = null,
)
