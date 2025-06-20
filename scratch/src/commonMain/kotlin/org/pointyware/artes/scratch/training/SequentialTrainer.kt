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

    override fun selectSamples(): List<StudyCase> {
        TODO("Delegate to optimizer")
    }

    override fun train(iterations: Int) {
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // ∂^L = (f^L)' \dot (a^L - y)
        val errors = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // Accumulates final output gradient for each epoch
        val aggregateLoss = Tensor.zeros(*network.layers.last().biases.dimensions)

        repeat(iterations) { epoch ->
            // Zero Gradients
            activations.forEach { it.mapEach { 0.0 } }
            derivativeActivations.forEach { it.mapEach { 0.0 } }
            errors.forEach { it.mapEach { 0.0 } }
            aggregateLoss.mapEach { 0.0 }

            // TODO: process by layer instead of by case
            // Process Each Case
            cases.forEach {
                // initialize accumulator to input case
                var networkLayerState = it.input

                // Forward Pass
                network.layers.forEachIndexed { layerIndex, layer ->
                    val weightedInputs = layer.forward(networkLayerState)
                    val activation = layer.activation.calculate(weightedInputs)
                    val derivativeActivation = layer.activation.derivative(weightedInputs)
                    activations[layerIndex] += activation
                    derivativeActivations[layerIndex] += derivativeActivation
                    networkLayerState = activation
                }
                // $\nabla_{a_L} C$ is the gradient of the loss function with respect to the final layer's output
                val lossGradient = lossFunction.derivative(expected = it.output, networkLayerState)
                // aggregate loss gradient across all cases
                aggregateLoss += lossGradient
            }

            var layerError = derivativeActivations.last() * aggregateLoss
            errors.last() += layerError

            // Backward Pass
            network.layers.indices.reversed().forEach back_pass@ { layerIndex ->
                // Skip the first layer as it has no previous layer to propagate error to
                if (layerIndex == 0) return@back_pass

                val layer = network.layers[layerIndex]
                val weightsTranspose = layer.weights.transpose()
                val layerActivation = activations[layerIndex]
                val layerDerivativeActivation = derivativeActivations[layerIndex]

                // Adjust parameters using the optimizer
                optimizer.update(
                    layer = layer,
                    activation = layerActivation,
                    derivative = layerDerivativeActivation,
                    error = layerError
                )

                // Propagate the error to the previous layer
                layerError = derivativeActivations[layerIndex - 1] *
                        weightsTranspose.matrixMultiply(layerError)
            }

            if (epoch % updatePeriod == 0) {
                println("Epoch $epoch, Loss: $aggregateLoss")
            }
        }
    }
}
