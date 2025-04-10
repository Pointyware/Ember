package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.HostConfig

/**
 * Complete information about a service to display in a detailed view.
 *
 * TODO: rename to HostInfoUiState - or other after rename
 *
 * @see [org.pointyware.artes.entities.HostConfig]
 */
data class HostUiState(
    val id: Long,
    val title: String
)

/**
 *
 */
fun HostConfig.toUiState(): HostUiState {
    return HostUiState(
        id = this.id,
        title = this.title,
    )
}
