package org.pointyware.artes.data.agents

import org.pointyware.artes.entities.Agent
import org.pointyware.artes.entities.Host

/**
 * Entry point to agent aggregate root
 */
interface AgentRepository {

    suspend fun createAgent(name: String, host: Host, model: String, instructions: String)
    suspend fun getAgent(id: Long): Agent
    suspend fun updateAgent(id: Long, name: String, host: Host, model: String, instructions: String)
    suspend fun deleteAgent(id: Long)
}
