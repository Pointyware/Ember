package org.pointyware.ember.entities.networks

import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.regularizers.Regularizer
import org.pointyware.ember.entities.tensors.Tensor

/**
 * Takes in a list of layers and allows splitting output layer into two
 * and joining them back together at a later layer.
 */
class ResidualSequentialNetwork(
    layers: List<LinearLayer>,
    val residualSplit: Int,
    val residualJoin: Int,
    val regularizer: Regularizer
): SequentialNetwork(layers) {

    override val parameterCount: Int
        get() = super.parameterCount + regularizer.parameterCount

    init {
        require(residualSplit >= 0) {
            "Residual split ($residualSplit) must be greater than or equal to 0"
        }
        require(residualSplit < residualJoin) {
            "Residual split ($residualSplit) must be less than residual join ($residualJoin)"
        }
        require(residualJoin < layers.size) {
            "Residual join ($residualJoin) must be less than number of layers (${layers.size})"
        }
    }

    override fun predict(input: Tensor): Tensor {
        lateinit var residualValue: Tensor
        return layers.foldIndexed(input) { index, acc, layer ->
            when (index) {
                residualSplit -> {
                    residualValue = layer.activationFunction.calculate(layer.forward(acc))
                    residualValue
                }
                residualJoin -> {
                    val residualInput = residualValue + acc
                    val normedInput = Tensor(residualInput.dimensions)
                    regularizer.predict(residualInput, normedInput)
                    layer.activationFunction.calculate(layer.forward(normedInput))
                }
                else -> {
                    layer.activationFunction.calculate(layer.forward(acc))
                }
            }
        }
    }

    private lateinit var residualSum: Tensor
    private lateinit var norm: Tensor
    private lateinit var normDerivative: Tensor
    override fun forward(
        input: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>
    ) {
        layers.foldIndexed(input) { index, previousLayer, layer ->
            val activation = activations[index]
            val derivativeActivation = derivativeActivations[index]
            when (index) {
                residualJoin -> {
                    residualSum = activations[residualSplit] + previousLayer
                    val layerSize = residualSum.dimensions[0]
                    norm = Tensor.zeros(layerSize, 1)
                    normDerivative = Tensor.zeros(layerSize, layerSize)
                    regularizer.forward(residualSum, norm, normDerivative)

                    layer.forward(norm, activation, derivativeActivation)
                }
                else -> { // residualSplit on forward pass does nothing special
                    layer.forward(previousLayer, activation, derivativeActivation)
                }
            }
            activation
        }
    }

    override fun backward(
        input: Tensor,
        error: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    ) {
        lateinit var residualValueError: Tensor
        var layerError = error
        layers.indices.reversed().forEach back_pass@ { index ->
            val priorActivation = if (index == 0) { input } else { activations[index - 1] }

            // Skip the first layer as it has no previous layer to propagate error to
            val priorActivationDerivative = if (index == 0) {
                Tensor.ones(input.dimensions)
            } else {
                derivativeActivations[index - 1]
            }
            val priorError = Tensor(priorActivation.dimensions)

            val layer = layers[index]
            val derivativeActivation = derivativeActivations[index]
            val weightGradients = weightGradients[index]
            val biasGradients = biasGradients[index]
            when (index) {
                residualJoin -> {
                    val regularizerError = Tensor(residualSum.dimensions)
                    // First pass through host layer to find error attributed to regularizer
                    layer.backward(
                        layerError,
                        norm, normDerivative,
                        weightGradients, biasGradients,
                        regularizerError
                    )

                    // back pass through regularizer to find error of sum and
                    //   error of residual input layers
                    regularizer.backward(
                        regularizerError,
                        residualSum,
                        derivativeActivation,
                        priorError,
                    )
                    residualValueError = priorError

                    layerError = priorError
                }
                residualSplit -> {
                    // First accumulate error from summed input error and following layer
                    layerError.plusAssign(residualValueError)

                    layer.backward(
                        layerError,
                        priorActivation, priorActivationDerivative,
                        weightGradients, biasGradients,
                        priorError
                    )
                    layerError = priorError
                }
                else -> {
                    layer.backward(
                        layerError,
                        priorActivation, priorActivationDerivative,
                        weightGradients, biasGradients,
                        priorError
                    )
                    layerError = priorError
                }
            }
        }
    }
}
