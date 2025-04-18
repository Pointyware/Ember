package org.pointyware.artes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase

/**
 * Allows a user to view existing service accounts and edit or remove them, or add new service
 * accounts.
 *
 * Must call [onInit] to start loading the data.
 */
interface ServiceListViewModel {

    val state: StateFlow<HostConfigListUiState>

    /**
     * Triggered when a view is initialized.
     */
    fun onInit()
}

/**
 *
 */
class DefaultServiceListViewModel(
    private val getHostServicesUseCase: GetHostServicesUseCase
): ViewModel(), ServiceListViewModel {
    private val mutableState = MutableStateFlow(HostConfigListUiState(emptyList()))
    override val state: StateFlow<HostConfigListUiState>
        get() = mutableState

    override fun onInit() {
        viewModelScope.launch {
            mutableState.value = HostConfigListUiState(
                loadingUiState = LoadingUiState.Loading
            )
            getHostServicesUseCase()
                .onSuccess {
                    mutableState.value = HostConfigListUiState(
                        hostConfigs = it.map { hostConfig ->
                            hostConfig.toUiState()
                        },
                        loadingUiState = LoadingUiState.Idle
                    )
                }
                .onFailure {  error ->
                    mutableState.value = HostConfigListUiState(
                        loadingUiState = LoadingUiState.Error(error.message ?: "Unknown error")
                    )
                }
        }
    }
}
