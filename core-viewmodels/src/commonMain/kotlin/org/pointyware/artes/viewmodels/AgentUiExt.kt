package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.Agent

/**
 *
 */
fun Agent.toUiState(): AgentInfoUiState {
    return AgentInfoUiState(
        id = this.id,
        name = this.name,
        host = this.model.hostConfig.toUiState(),
        model = this.model.toUiState(),
    )
}
