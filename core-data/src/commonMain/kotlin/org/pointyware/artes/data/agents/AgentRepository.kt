package org.pointyware.artes.data.agents

import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.entities.Agent
import org.pointyware.artes.entities.Host

/**
 * Entry point to agent aggregate root
 */
interface AgentRepository {

    suspend fun createAgent(name: String, host: Host, instructions: String)
    suspend fun getAgent(id: Long): Agent
    suspend fun updateAgent(id: Long, name: String, host: Host, instructions: String)
    suspend fun deleteAgent(id: Long)
}

class AgentRepositoryImpl(
    lazyDb: Lazy<HostDb>
): AgentRepository {

    private val db by lazyDb

    override suspend fun createAgent(
        name: String,
        host: Host,
        instructions: String
    ) {
        db.agentsQueries.createAgent(
            name = name,
            service = host.id,
            instructions = instructions
        )
    }

    override suspend fun getAgent(id: Long): Agent {
        return db.agentsQueries.getAgent(id).executeAsOne().let {
            Agent(
                id = it.id,
                name = it.name,
                host = TODO(),
                skills = emptySet()
            )
        }
    }

    override suspend fun updateAgent(
        id: Long,
        name: String,
        host: Host,
        instructions: String
    ) {
        db.agentsQueries.updateAgent(id = id, name = name, service = host.id, instructions = instructions)
    }

    override suspend fun deleteAgent(id: Long) {
        db.agentsQueries.deleteAgent(id)
    }
}
