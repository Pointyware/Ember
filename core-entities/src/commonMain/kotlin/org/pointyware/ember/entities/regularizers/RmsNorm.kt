package org.pointyware.ember.entities.regularizers

import org.pointyware.ember.entities.tensors.Tensor

/**
 * RmsNorm is typically used within a layer to normalize the activations from a previous
 * layer or sum of activations before calculating the preactivation weighted sum.
 *
 * Normalizers can also be seen as dedicated layers with an activation function that
 * acts on the whole instead of the parts, without weights or biases.
 */
object RmsNorm: Regularizer {
    override fun predict(
        input: Tensor,
        output: Tensor
    ) {
        TODO("Not yet implemented")
    }

    override fun forward(
        input: Tensor,
        output: Tensor,
        derivative: Tensor
    ) {
        TODO("Not yet implemented")
    }

    override fun backward(
        error: Tensor,
        activation: Tensor,
        priorError: Tensor
    ) {
        TODO("Not yet implemented")
    }
}
