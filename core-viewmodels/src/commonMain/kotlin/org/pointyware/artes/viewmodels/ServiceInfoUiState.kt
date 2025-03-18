package org.pointyware.artes.viewmodels

/**
 * Complete information about a service to display in a detailed view.
 *
 * @see [org.pointyware.artes.core.entities.Service]
 */
data class ServiceInfoUiState(
    val id: String,
    val title: String,
    val url: String
)
