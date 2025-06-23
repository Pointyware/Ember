package org.pointyware.ember.layers

import org.pointyware.ember.tensors.Tensor

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
    fun forward(input: Tensor): Tensor
}
