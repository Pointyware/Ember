package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.Host
import org.pointyware.artes.entities.HostConfig

/**
 * Complete information about a service to display in a detailed view.
 *
 * @see [org.pointyware.artes.entities.HostConfig]
 */
data class HostConfigUiState(
    val id: Long,
    val title: String,
    val host: Host
)

/**
 *
 */
fun HostConfig.toUiState(): HostConfigUiState {
    return HostConfigUiState(
        id = this.id,
        title = this.title,
        host = this.host
    )
}
