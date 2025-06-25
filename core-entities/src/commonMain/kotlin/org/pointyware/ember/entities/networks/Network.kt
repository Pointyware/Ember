package org.pointyware.ember.entities.networks

import org.pointyware.ember.entities.tensors.Tensor

/**
 * A network is collection of neurons that can be arranged in a wide variety of ways. Most often
 * they are arranged in layers, but they can also be arranged in more complex structures with
 * branches, skips, and other connections.
 *
 * experiment with generics; Network<Residuals>
 * fun forward(input: Tensor, residuals: Residuals)
 * fun backward(error: Tensor, residuals: Residuals)
 * fun updateWeights(residuals: Residuals)
 */
interface Network {
    /**
     * Takes the input and performs a forward pass through the network, returning the output
     * as a [Tensor].
     */
    fun predict(input: Tensor): Tensor

    /**
     * Takes the input and performs a forward pass through the network, using the given
     * [activation] and [derivativeActivation] tensor lists to store the results.
     */
    fun forward(input: Tensor, activations: List<Tensor>, derivativeActivations: List<Tensor>)

    /**
     * Takes the gradient of the loss function given as [error], computing the gradients
     * and using the given [weightGradients] and [biasGradients] lists to store the results.
     */
    fun backward(
        input: Tensor,
        error: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    )
}
