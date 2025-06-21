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
        val weightGradients = network.layers.map { Tensor.zeros(*it.weights.dimensions) }
        val biasGradients = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // ∂^L = (f^L)' \dot (a^L - y)
        val errors = network.layers.map { Tensor.zeros(*it.biases.dimensions) }

        repeat(iterations) { epoch ->
            // Create Gradient tensors
            weightGradients.forEach { it.mapEach { 0.0 } }
            biasGradients.forEach { it.mapEach { 0.0 } }

            // TODO: use optimizer.sample to select samples
            cases.forEach { case ->
                // Zero tensors for activations, derivativeActivations, and errors
                activations.forEach { it.mapEach { 0.0 } }
                derivativeActivations.forEach { it.mapEach { 0.0 } }
                errors.forEach { it.mapEach { 0.0 } }

                // Forward Pass - using tensors as gradient receivers
                network.forward(case.input, activations, derivativeActivations)
                val output = activations.last()
                // $\nabla_{a_L} C$ is the gradient of the loss function with respect to the final layer's output
                errors.last() += derivativeActivations.last() * lossFunction.derivative(expected = case.output, output)

                // Backward Pass - using tensors as gradient sources
                network.backward(errors.last(), derivativeActivations, weightGradients, biasGradients)
            }
            // FIXME: Accumulate gradients

            // FIXME: Update parameters using optimizer
            // Adjust parameters for all layers using the optimizer
            for (layer in network.layers) {
                optimizer.update(
                    layer = layer,
                    priorActivationDerivative = derivativeActivations[layerIndex - 1],
                    error = layerError
                )
            }

            // Output progress -
            // calculate the loss for this epoch
            if (epoch % updatePeriod == 0) {
                println("Epoch $epoch, Loss: $aggregateLoss")
            }
        }
    }
}
