package org.pointyware.artes.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.artes.interactors.BeginServiceConfigJourney
import org.pointyware.artes.interactors.RemoveServiceConfigUseCase

/**
 * Allows a user to view existing service accounts and edit or remove them, or add new service
 * accounts.
 */
interface ServiceListViewModel {
    data class UiState(
        val accounts: List<HostUiState>
    )

    val state: StateFlow<UiState>

    /**
     * Triggered when a user selects a service to be removed.
     */
    fun onRemoveService(id: String)

    /**
     * Triggered when a user wants to register a new service.
     */
    fun onRegisterService()
}

/**
 *
 */
class DefaultServiceListViewModel(
    private val beginServiceConfigJourney: BeginServiceConfigJourney,
    private val removeServiceConfigUseCase: RemoveServiceConfigUseCase
): ServiceListViewModel {
    private val mutableState = MutableStateFlow(ServiceListViewModel.UiState(emptyList()))
    override val state: StateFlow<ServiceListViewModel.UiState>
        get() = mutableState

    override fun onRemoveService(id: String) {
        println("removeService: $id")
    }

    override fun onRegisterService() {
        println("onRegisterService")
    }
}
