package org.pointyware.ember.viewmodels

import org.pointyware.ember.entities.activations.ActivationFunction

data class LayerUiState(
    val weights: List<List<Float>>,
    val biases: List<Float>,
    val activationFunction: ActivationFunction
)
