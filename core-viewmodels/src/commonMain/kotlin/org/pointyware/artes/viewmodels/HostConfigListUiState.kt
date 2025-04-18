package org.pointyware.artes.viewmodels

/**
 */
data class HostConfigListUiState(
    val hostConfigs: List<HostConfigUiState> = emptyList(),
    val loadingUiState: LoadingUiState = LoadingUiState.Idle
)
