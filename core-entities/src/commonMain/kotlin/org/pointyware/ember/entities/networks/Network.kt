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
//
//    /**
//     * Returns a map of calculation tensors identified by [Long] ids. Ids usually
//     * correspond to layers; however, some networks have intermediate calculations
//     * in addition to layers which can also be retrieved by id.
//     */
//    fun prepare(): Map<Long, Tensor>
//
//    /**
//     * Represents one or more edges in a calculation graph that produce
//     * single output in terms of one or more inputs.
//     */
//    data class Calculation(val inputs: List<Long>, val output: Long)
//    /**
//     * Returns a list of forward pass calculations in depth-first order
//     * to guarantee all dependencies are ready.
//     */
//    fun forwardGraph(): List<Calculation>
//    /**
//     * Returns a list of backward pass calculations in depth-first order
//     * to guarantee all dependencies are ready.
//     */
//    fun backwardGraph(): List<Calculation>
//    fun forward(inputs: List<Tensor>, output: Tensor, calculations: Map<Long, Tensor>)
//    fun backward(errors: List<Tensor>, output: Tensor, calculations: Map<Long, Tensor>)

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
