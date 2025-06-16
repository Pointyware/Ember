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
    private val updatePeriod: Int = 100
) {

    fun train(iterations: Int) {
        val activations = network.layers.map { Tensor.shape(*it.weights.dimensions) }
        val derivativeActivations = network.layers.map { Tensor.shape(*it.weights.dimensions) }

        repeat(iterations) { epoch ->
            // Zero Gradients
            activations.forEach { it.mapEach { 0.0 } }
            derivativeActivations.forEach { it.mapEach { 0.0 } }

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
                var backwardOutput = Tensor.shape(*networkLayerState.dimensions).mapEach { 1.0 }

                }
                aggregateLoss += lossFunction.compute(expected = it.output, output)

                // Backward Pass
                for (index in network.layers.indices.reversed()) {
                    // Update Weights and Biases
                    optimizer.update(
                        network.layers[index],
                        activations[index],
                        derivativeActivations[index]
                    )
                }
            }

            if (epoch % updatePeriod == 0) {
                println("Epoch $epoch, Loss: $aggregateLoss")
            }
        }
    }
}
