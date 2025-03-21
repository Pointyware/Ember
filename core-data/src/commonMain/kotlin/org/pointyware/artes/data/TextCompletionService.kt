package org.pointyware.artes.data

/**
 * Describes the interactions available on all text completion services.
 */
interface TextCompletionService {
    suspend fun complete(prompt: String): String
}
