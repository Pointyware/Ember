package org.pointyware.ember.entities.layers

import org.pointyware.ember.entities.tensors.Tensor

/**
 * A single layer in a neural network. These can be simple layers like
 * dense layers, or aggregates like transformers or convolutional.
 *
 * TODO: consider splitting concerns into Learning/TrainingLayer and InferenceLayer?
 */
interface Layer {
    /**
     * Forward pass through the layer.
     *
     * @param input The input tensor to the layer.
     * @return The output tensor after calculations.
     */
    fun predict(input: Tensor, output: Tensor)

    /**
     * Forward pass through the layer.
     *
     * @param input The input tensor to the layer.
     * @return The output tensor after calculations.
     */
    fun forward(input: Tensor): Tensor

    /**
     * Forward pass through the layer.
     * @param input Either the original network input or the activation from a previous layer.
     * @param activation The output from this layer.
     * @param derivative The derivative of the output of this layer for the given input.
     */
    fun forward(input: Tensor, activation: Tensor, derivative: Tensor)

    /**
     * Performs the layer-specific operations of the back pass. The layer takes in the
     * error attributed to it, calculated at `l+1`, the downstream layer (for hidden layers),
     * or the loss function (for output layers).
     *
     * In order to propagate the error backwards, the activations from the upstream layer `l-1`
     * are provided. These are usually matrix multiplied with the weights
     *
     * @param error The upstream error tensor.
     * @param priorActivation The activation from the upstream layer.
     * @param priorActivationDerivative The derivative of the activation from the upstream layer.
     * @param weightGradient Receives the gradient of the loss with respect to the weights.
     * @param biasGradient Receives the gradient of the loss with respect to the biases.
     * @param priorError Receives the error to propagate backwards.
     */
    fun backward(
        error: Tensor,
        priorActivation: Tensor,
        priorActivationDerivative: Tensor,
        weightGradient: Tensor,
        biasGradient: Tensor,
        priorError: Tensor,
    )
}
