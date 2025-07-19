package org.pointyware.ember.entities.activations

import org.pointyware.ember.entities.tensors.Tensor

/**
 *
 */
class SoftMax: ActivationFunction {
    override fun calculate(input: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    override fun calculate(
        input: Tensor,
        activation: Tensor,
        derivativeActivation: Tensor
    ) {
        TODO("Not yet implemented")
    }

    override fun derivative(input: Tensor): Tensor {
        TODO("Not yet implemented")
    }
}
