package org.pointyware.artes.viewmodels

/**
 */
data class HostConfigListUiState(
    val hostConfigs: List<HostUiState> = emptyList(),
    val loadingUiState: LoadingUiState = LoadingUiState.Idle
)
