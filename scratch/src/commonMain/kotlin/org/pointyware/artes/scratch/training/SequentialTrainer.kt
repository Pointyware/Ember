package org.pointyware.artes.scratch.training

import org.pointyware.artes.scratch.loss.LossFunction
import org.pointyware.artes.scratch.networks.SequentialNetwork
import org.pointyware.artes.scratch.optimizers.Optimizer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 *
 */
class SequentialTrainer(
    val network: SequentialNetwork,
    val cases: List<StudyCase>,
    val lossFunction: LossFunction,
    val optimizer: Optimizer,
    override val updatePeriod: Int = 100
): Trainer {

    override fun train(iterations: Int) {
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { Tensor.zeros(*it.weights.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { Tensor.zeros(*it.weights.dimensions) }
        // ∂^L = (f^L)' \dot (a^L - y)
        val errors = network.layers.map { Tensor.zeros(*it.weights.dimensions) }

        repeat(iterations) { epoch ->
            // Zero Gradients
            activations.forEach { it.mapEach { 0.0 } }
            derivativeActivations.forEach { it.mapEach { 0.0 } }
            errors.forEach { it.mapEach { 0.0 } }

            // TODO: process by layer instead of by case
            // Process Each Case
            var aggregateLoss = 0.0
            cases.forEach {
                // initialize accumulator to input case
                var networkLayerState = it.input

                // Forward Pass
                network.layers.forEachIndexed { layerIndex, layer ->
                    networkLayerState = layer.forward(networkLayerState)
                    activations[layerIndex] += layer.activation.calculate(networkLayerState)
                    derivativeActivations[layerIndex] += layer.activation.derivative(networkLayerState)
                }
                aggregateLoss += lossFunction.compute(expected = it.output, networkLayerState)

                // initialize loss gradient
                val lossGradient = networkLayerState - it.output
                var layerError = derivativeActivations.last() * lossGradient

                // Backward Pass
                network.layers.asReversed().forEachIndexed { layerIndex, layer ->
                    val reversedIndex = network.layers.size - 1 - layerIndex
                    val layerOutput = activations[reversedIndex]
                    val previousLayerDerivative = derivativeActivations[reversedIndex - 1]
                    if (layerIndex == 0) {
                        // For the last layer, we use the loss gradient directly
                        layerError = lossGradient * layer.activation.derivative(networkLayerState)
                    } else {
                        // For other layers, we calculate the error based on the previous layer's output
                        val previousLayerOutput = activations[network.layers.size - 1 - (layerIndex - 1)]
                        layerError = layerError * layer.activation.derivative(previousLayerOutput)
                    }

                    layerError = previousLayerDerivative * layer.weights.transpose() * layerError

                    layerError = optimizer.update(
                        layer = layer,
                        activation = layerOutput,
                        derivative = previousLayerDerivative
                    )
                }
            }

            if (epoch % updatePeriod == 0) {
                println("Epoch $epoch, Loss: $aggregateLoss")
            }
        }
    }
}
