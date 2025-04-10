package org.pointyware.artes.services.openai.network

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource("completions/")
object Completions {

    /**
     * https://platform.openai.com/docs/api-reference/completions/create
     *
     * @param model ID of the model to use. You can use the List models API to see all of your available models.
     * @param prompt The prompt(s) to generate completions for, encoded as a string.
     * To support: array of strings, array of tokens, or array of token arrays.
     * @param suffix The suffix that comes after a completion of inserted text.
     * @param maxTokens The maximum number of tokens to generate.
     * @param temperature What sampling temperature to use, between 0 and 2. Higher values means the model will take more risks.
     * @param topProbability Top probabilities to consider, between 0 and 1. An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with highest probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * @param completionCount How many completions to generate for each prompt.
     * @param stream Whether to stream back partial progress.
     * @param logProbs Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens. Between 0 and 5.
     * @param echo Echo back the prompt in addition to the completion.
     * @param stop Up to 4 sequences where the API will stop generating further tokens. // TODO: support array of strings
     * @param presencePenalty How much to penalize new tokens based on whether they appear in the text so far. Between -2.0 and 2.0.
     * @param frequencyPenalty How much to penalize new tokens based on their existing frequency in the text so far. Between -2.0 and 2.0
     * @param bestOf Generates bestOf completions server-side and returns the "best" (the one with the lowest log probability per token). Results cannot be streamed.
     * @param logitBias Map of tokens to logit bias values. A map of token (specified by token ID or string) to the desired bias.
     * @param user The user ID for which the request is being made. // TODO: support string
     */
    @Serializable
    data class Request(
        val model: String,
        val prompt: String? = null,
        val suffix: String? = null,
        val maxTokens: Int? = null,
        val temperature: Float? = null,
        val topProbability: Float? = null,
        val completionCount: Int? = null,
        val stream: Boolean? = null,
        val logProbs: Int? = null,
        val echo: Boolean? = null,
        val stop: String? = null,
        val presencePenalty: Float? = null,
        val frequencyPenalty: Float? = null,
        val bestOf: Int? = null,// must be greater than completionCount
        val logitBias: Map<String, Float>? = null,
        val user: String? = null
    ) {
        companion object {
            const val DEFAULT_PROMPT = "<|endoftext|>"
            const val DEFAULT_MAX_TOKENS = 16
            const val DEFAULT_TEMPERATURE = 1f
            const val DEFAULT_TOP_P = 1f
            const val DEFAULT_N = 1
            const val DEFAULT_STREAM = false
            val DEFAULT_LOGPROBS = null
            const val DEFAULT_ECHO = false
            val DEFAULT_STOP = null
            const val DEFAULT_PRESENCE_PENALTY = 0f
            const val DEFAULT_FREQUENCY_PENALTY = 0f
            const val DEFAULT_BEST_OF = 1
        }
    }

}
