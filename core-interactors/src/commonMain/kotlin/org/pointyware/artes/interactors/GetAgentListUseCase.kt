package org.pointyware.artes.interactors

import org.pointyware.artes.data.agents.AgentRepository
import org.pointyware.artes.entities.Agent

class GetAgentListUseCase(
    private val agentRepository: AgentRepository,
) {
    suspend operator fun invoke(): Result<List<Agent>> = runCatching {
        agentRepository.getAgentList()
    }
}
