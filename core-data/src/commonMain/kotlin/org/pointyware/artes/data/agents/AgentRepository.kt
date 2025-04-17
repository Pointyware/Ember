package org.pointyware.artes.data.agents

import org.pointyware.artes.data.hosts.ServiceRepository
import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.entities.Agent
import org.pointyware.artes.entities.Model

/**
 * Entry point to agent aggregate root
 */
interface AgentRepository {
    suspend fun createAgent(name: String, model: Model, instructions: String)
    suspend fun getAgent(id: Long): Agent
    suspend fun updateAgent(id: Long, name: String, model: Model, instructions: String)
    suspend fun deleteAgent(id: Long)
    suspend fun getAgentList(): List<Agent>
}

class AgentRepositoryImpl(
    lazyDb: Lazy<HostDb>,
    private val serviceRepository: ServiceRepository
): AgentRepository {

    private val db by lazyDb

    override suspend fun createAgent(name: String, model: Model, instructions: String) {
        db.agentsQueries.createAgent(
            name = name,
            model = model.id,
            instructions = instructions
        )
    }

    override suspend fun getAgent(id: Long): Agent {
        return db.agentsQueries.getAgent(id).executeAsOne().let {
//            val host = serviceRepository.getService(it.service)
//            val model = serviceRepository.getModels(host).get(it.service)
            Agent(
                id = it.id,
                name = it.name,
                model = serviceRepository.getModel(it.model),
                skills = emptySet()
            )
        }
    }

    override suspend fun getAgentList(): List<Agent> {
        return db.agentsQueries.getAllAgents().executeAsList().let {
            it.map { agent ->
                Agent(
                    id = agent.id,
                    name = agent.name,
                    model = serviceRepository.getModel(agent.model),
                    skills = emptySet()
                )
            }
        }
    }

    override suspend fun updateAgent(id: Long, name: String, model: Model, instructions: String) {
        db.agentsQueries.updateAgent(id = id, name = name, model = model.id, instructions = instructions)
    }

    override suspend fun deleteAgent(id: Long) {
        db.agentsQueries.deleteAgent(id)
    }
}
