package org.pointyware.ember.viewmodels

data class NeuralNetworkUiState(
    val layers: List<LayerUiState> = emptyList(),
    val parameters: Map<String, Any> = emptyMap()
)
