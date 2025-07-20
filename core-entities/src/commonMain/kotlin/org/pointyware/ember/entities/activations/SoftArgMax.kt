package org.pointyware.ember.entities.activations

import org.pointyware.ember.entities.tensors.Tensor
import kotlin.math.exp

/**
 * Transforms the raw output of a layer into a probability distribution. All outputs
 * are non-negative and sum to 1. Each individual component is calculated as the
 * exponential of the raw output divided by the sum of the exponentials of all inputs.
 */
class SoftArgMax: ActivationFunction {
    override fun calculate(input: Tensor): Tensor {
        val exponentials = input.mapEach { exp(it) }
        val sum = exponentials.values.asSequence().sum()
        return exponentials.mapEach { it / sum }
    }

    override fun calculate(
        input: Tensor,
        activation: Tensor,
        derivativeActivation: Tensor
    ) {
        val exponentials = input.mapEach { exp(it) }
        val sum = exponentials.values.asSequence().sum()
        activation.mapEachFlatIndexed { index, _ -> exponentials[index] / sum }
        derivativeActivation.mapEachFlatIndexed { index, _ ->
            val p = activation[index]
            p * (1 - p)
        }
    }

    override fun derivative(input: Tensor): Tensor {
        val exponentials = input.mapEach { exp(it) }
        val sum = exponentials.values.asSequence().sum()
        return exponentials.mapEach { it / sum }.mapEach { it * (1 - it) }
    }
}
