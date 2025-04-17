package org.pointyware.artes.viewmodels

/**
 *
 */
sealed interface LoadingUiState {
    data object Idle: LoadingUiState
    data object Loading : LoadingUiState
    data class Error(val message: String) : LoadingUiState
}
