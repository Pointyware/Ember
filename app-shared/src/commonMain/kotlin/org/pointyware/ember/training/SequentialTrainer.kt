package org.pointyware.ember.training

import org.pointyware.ember.loss.LossFunction
import org.pointyware.ember.networks.SequentialNetwork
import org.pointyware.ember.optimizers.Optimizer
import org.pointyware.ember.tensors.Tensor

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
        // W^L
        val weightGradients = network.layers.map { Tensor.zeros(*it.weights.dimensions) }
        // b^L
        val biasGradients = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }

        repeat(iterations) { epoch ->
            // Create Gradient tensors
            weightGradients.forEach { it.mapEach { 0.0 } }
            biasGradients.forEach { it.mapEach { 0.0 } }

            var aggregateLoss = 0.0
            val caseCount = cases.size.toDouble()
            cases.forEach { case ->
                // Zero tensors for activations, derivativeActivations, and errors
                activations.forEach { it.mapEach { 0.0 } }
                derivativeActivations.forEach { it.mapEach { 0.0 } }

                // Forward Pass - using tensors as gradient receivers
                network.forward(case.input, activations, derivativeActivations)

                // Calculate the loss for the current case
                val output = activations.last()
                val loss = lossFunction.compute(expected = case.output, actual = output)
                aggregateLoss += loss
                // $\nabla_{a_L} C$ is the gradient of the loss function with respect to the final layer's output
                val errorGradient = lossFunction.derivative(expected = case.output, actual = output)

                // Backward Pass
                network.backward(case.input, errorGradient, activations, derivativeActivations, weightGradients, biasGradients)
            }
            // Average the gradients over all cases
            weightGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }
            biasGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }

            // Adjust parameters for all layers using the optimizer
            network.layers.forEachIndexed { index, layer ->
                optimizer.update(layer, weightGradients[index], biasGradients[index])
            }

            // Output progress -
            // calculate the loss for this epoch
            val averageLoss = aggregateLoss / caseCount
            if (epoch % updatePeriod == 0 || epoch == iterations - 1) {
                println("Epoch $epoch, Loss: $averageLoss")
                println("Network parameters: {")
                network.layers.forEachIndexed { index, layer ->
                    println("Layer $index: Weights: ${layer.weights}, Biases: ${layer.biases}")
                }
                println("}")
            }
        }
    }
}
