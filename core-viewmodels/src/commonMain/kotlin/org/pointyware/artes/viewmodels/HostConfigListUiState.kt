package org.pointyware.artes.viewmodels

/**
 */
data class HostConfigListUiState(
    val hostConfigs: List<HostUiState>,
    val loadingUiState: LoadingUiState
)
