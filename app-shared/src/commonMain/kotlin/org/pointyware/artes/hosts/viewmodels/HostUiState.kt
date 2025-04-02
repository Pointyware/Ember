package org.pointyware.artes.hosts.viewmodels

import org.pointyware.artes.entities.hosts.Host

/**
 *
 */
data class HostUiState(
    val id: Long,
    val title: String
)

fun mapToUiState(input: Host): HostUiState {
    return HostUiState(
        id = input.id,
        title = input.title
    )
}
