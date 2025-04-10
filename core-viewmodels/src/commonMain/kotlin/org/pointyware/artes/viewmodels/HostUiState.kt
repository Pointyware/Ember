package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.Host

/**
 * Complete information about a service to display in a detailed view.
 *
 * TODO: rename to HostInfoUiState - or other after rename
 *
 * @see [org.pointyware.artes.entities.Host]
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
