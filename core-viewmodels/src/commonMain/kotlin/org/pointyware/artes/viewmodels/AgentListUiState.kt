package org.pointyware.artes.viewmodels

/**
 *
 */
data class AgentListUiState(
    val agents: List<AgentInfoUiState> = emptyList(),
    val loading: LoadingUiState = LoadingUiState.Idle,
)
