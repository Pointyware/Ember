package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.Model

/**
 *
 */
data class ModelUiState(
    val id: Long,
    val name: String // TODO: rename to name
)

fun Model.toUiState(): ModelUiState {
    return ModelUiState(
        id = id,
        name = name
    )
}
