package org.pointyware.artes.data.hosts

import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.Model

/**
 * Repository for managing hosts. Mediates between multiple data sources.
 *
 * Currently supported hosts:
 * - OpenAI
 * - Gemini
 */
interface ServiceRepository {
    suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String)
    suspend fun createGeminiHost(title: String, apiKey: String)
    suspend fun getHosts(): List<HostConfig>
    suspend fun getService(id: Long): HostConfig
    suspend fun getModel(id: Long): Model
    suspend fun getModels(hostId: Long): List<Model>
    suspend fun removeService(id: Long)
}
