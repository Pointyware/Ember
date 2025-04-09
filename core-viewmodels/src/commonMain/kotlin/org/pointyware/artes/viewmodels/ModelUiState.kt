package org.pointyware.artes.viewmodels

import org.pointyware.artes.entities.Model

/**
 *
 */
data class ModelUiState(
    val id: Long,
    val model: String,
)

fun Model.toUiState(): ModelUiState {
    return ModelUiState(
        id = id,
        model = name
    )
}
