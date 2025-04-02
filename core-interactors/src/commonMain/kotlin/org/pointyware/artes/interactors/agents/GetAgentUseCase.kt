package org.pointyware.artes.interactors.agents

import org.pointyware.artes.data.agents.AgentRepository
import org.pointyware.artes.entities.Agent

/**
 *
 */
class GetAgentUseCase(
    private val agentRepository: AgentRepository
) {

    suspend operator fun invoke(id: Long): Result<Agent> = runCatching {
        agentRepository.getAgent(id)
    }
}
