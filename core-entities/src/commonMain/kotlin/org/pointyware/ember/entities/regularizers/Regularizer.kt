package org.pointyware.ember.entities.regularizers

import org.pointyware.ember.entities.tensors.Tensor

/**
 * A regularizer is typically used within a layer to regularize the activations from a previous
 * layer or sum of activations before the real layer calculates its preactivation weighted sum.
 *
 * Regularizers can also be seen as dedicated layers with an activation function that
 * acts on the whole instead of the parts, without weights or biases.
 *
 * Regularization in general is the ability of some method to simplify an otherwise complex
 * problem. Stochastic Gradient Descent is an example of implicit regularization, along with
 * many other modern AI techniques. Dedicated regularizers are explicit.
 */
interface Regularizer {

    val parameterCount: Int

    /**
     * Performs a forward-pass retaining no intermediate calculations.
     */
    fun predict(input: Tensor, output: Tensor)

    /**
     * @param input The raw input to the regularizer.
     * @param output The output tensor that receives the regularized output.
     * @param derivative The output tensor that receives the derivative of the regularization
     * for the given input.
     */
    fun forward(input: Tensor, output: Tensor, derivative: Tensor)

    /**
     * @param error The error gradient attributed to this operation or pseudo-layer from
     * the upstream gradient.
     * @param priorActivation The activation that fed into this regularizer.
     * @param priorActivationDerivative The derivative of the activation that fed into this
     * regularizer.
     * @param priorError The output tensor that receives the computation of this back pass
     * for the downstream gradient.
     */
    fun backward(
        error: Tensor,
        priorActivation: Tensor,
        priorActivationDerivative: Tensor,
        priorError: Tensor
    )
}
