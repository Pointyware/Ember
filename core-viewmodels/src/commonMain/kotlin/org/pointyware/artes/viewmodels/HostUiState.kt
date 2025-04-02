package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.hosts.Host

/**
 * Complete information about a service to display in a detailed view.
 *
 * @see [org.pointyware.artes.entities.hosts.Host]
 */
data class HostUiState(
    val id: Long,
    val title: String
)

/**
 *
 */
fun Host.toUiState(): HostUiState {
    return HostUiState(
        id = this.id,
        title = this.title,
    )
}
