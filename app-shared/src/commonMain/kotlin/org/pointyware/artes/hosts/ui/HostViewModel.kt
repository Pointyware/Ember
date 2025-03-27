package org.pointyware.artes.hosts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.pointyware.artes.hosts.interactors.CreateHostUseCase

/**
 *
 */
class HostViewModel(
    private val createHostUseCase: CreateHostUseCase,
): ViewModel() {

    fun createHost(title: String, orgId: String, key: String) {
        viewModelScope.launch {
            println("Creating host with title: $title, orgId: $orgId, key: ${"*".repeat(key.length)}")
            createHostUseCase(title, orgId, key)
                .onSuccess {
                    println("Host created successfully")
                }
                .onFailure {
                    println("Failed to create host: ${it.message}")
                }
        }
    }
}
