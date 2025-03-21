package org.pointyware.artes.services.openai.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API quota used by a request/response.
 */
@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int
)
