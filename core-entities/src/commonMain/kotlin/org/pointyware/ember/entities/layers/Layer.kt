package org.pointyware.ember.entities.layers

import org.pointyware.ember.entities.tensors.Tensor

/**
 * A single layer in a neural network. These can be simple layers like
 * dense layers, or aggregates like transformers or convolutional.
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
}
