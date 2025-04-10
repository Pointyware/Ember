package org.pointyware.artes.services.openai.network

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Allows a user to retrieve a list of all models available in the OpenAI API.
 *
 * https://platform.openai.com/docs/api-reference/models/list
 */
@Resource("models/")
object Models

/**
 * Response returned from [Models] endpoint.
 */
@Serializable
data class ModelsResponse(
    val `object`: String,
    val data: List<ModelDto>
)

/**
 * Represents a single OpenAI model returned from the API.
 */
@Serializable
data class ModelDto(
    val id: String,
    val `object`: String,
    @SerialName("owned_by")
    val ownedBy: String,
    val created: Long,
)
