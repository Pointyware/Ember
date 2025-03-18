package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.HostedService
import org.pointyware.artes.entities.Service

/**
 *
 */
fun Service.toUiState(): ServiceInfoUiState {
    val url =
        when (this) {
            is HostedService -> {
                this.host
            }
            else -> {
                "Not Hosted"
            }
        }
    return ServiceInfoUiState(id = this.id, title = this.name, url = url)
}
