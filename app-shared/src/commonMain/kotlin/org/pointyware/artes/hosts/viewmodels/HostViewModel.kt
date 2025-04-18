package org.pointyware.artes.hosts.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.hosts.interactors.CreateHostUseCase
import org.pointyware.artes.viewmodels.LoadingUiState

/**
 *
 */
class HostViewModel(
    private val createHostUseCase: CreateHostUseCase,
): ViewModel() {

    private val _onHostCreated = Channel<Unit>(1)
    val onHostCreated: Flow<Unit> get() = _onHostCreated.consumeAsFlow()

    private val _state = MutableStateFlow(HostConfigUiState(loading = LoadingUiState.Idle))
    val state: StateFlow<HostConfigUiState> get() = _state.asStateFlow()

    fun createHost(title: String, extraOptions: ExtraOptionsUiState) {
        viewModelScope.launch {
            val orgId = when (extraOptions) {
                is ExtraOptionsUiState.OpenAi -> extraOptions.orgId
                is ExtraOptionsUiState.Gemini -> ""
            }
            val key = when (extraOptions) {
                is ExtraOptionsUiState.OpenAi -> extraOptions.apiKey
                is ExtraOptionsUiState.Gemini -> extraOptions.apiKey
            }
            println("Creating host with title: $title, orgId: $orgId, key: ${"*".repeat(key.length)}")
            _state.update {
                it.copy(loading = LoadingUiState.Loading)
            }
            createHostUseCase(title, orgId, key)
                .onSuccess {
                    _state.update {
                        it.copy(loading = LoadingUiState.Idle)
                    }
                    _onHostCreated.send(Unit)
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(loading = LoadingUiState.Error(error.message ?: "Unknown error"))
                    }
                }
        }
    }
}
