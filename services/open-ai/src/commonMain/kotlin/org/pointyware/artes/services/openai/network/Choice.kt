package org.pointyware.artes.services.openai.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes one possible completion returned by the OpenAI API.
 */
@Serializable
data class Choice(
    val text: String,
    val index: Int,
    @SerialName("finish_reason")
    val finishReason: String
)
