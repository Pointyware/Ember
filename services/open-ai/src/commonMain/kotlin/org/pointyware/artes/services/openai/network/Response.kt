package org.pointyware.artes.services.openai.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the OpenAI API for a completion request.
 *
 * @property id The ID of the completion request.
 * @property type The object type of the completion request.
 * @property created The time the completion request was created (Unix).
 * @property model The model used for the completion request.
 * @property choices The list of choices returned by the completion request.
 * @property usage The usage information for the completion request.
 */
@Serializable
data class Response(
    val id: String,
    @SerialName("object")
    val type: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)
