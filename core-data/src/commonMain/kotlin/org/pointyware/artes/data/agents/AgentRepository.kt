package org.pointyware.artes.data.agents

import org.pointyware.artes.data.hosts.db.HostDb
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

class AgentRepositoryImpl(
    lazyDb: Lazy<HostDb>
): AgentRepository {

    private val db by lazyDb

    override suspend fun createAgent(
        name: String,
        host: Host,
        model: String,
        instructions: String
    ) {
        db.agentsQueries.createAgent(
            name = name,
//            hostId = host.id,
//            model = model,
//            instructions = instructions
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
        model: String,
        instructions: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAgent(id: Long) {
        db.agentsQueries.deleteAgent(id)
    }
}
