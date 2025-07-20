package org.pointyware.ember.entities.activations

import org.pointyware.ember.entities.tensors.Tensor
import kotlin.math.exp

/**
 * Logistic function, also known as "the" sigmoid function; although,
 * a sigmoid function is any function that has an "S" shaped curve.
 */
object Logistic: ScalarActivationFunction {
    override val parameterCount: Int
        get() = 0

    override fun scalarActivation(input: Float): Float {
        // Sigmoid function: f(x) = 1 / (1 + exp(-x))
        return 1.0f / (1.0f + exp(-input))
    }

    override fun scalarDerivative(input: Float): Float {
        // Derivative of sigmoid: f'(x) = f(x) * (1 - f(x))
        val sigmoidValue = scalarActivation(input)
        return sigmoidValue * (1.0f - sigmoidValue)
    }

    override fun calculate(input: Tensor, activation: Tensor, derivativeActivation: Tensor) {
        input.flatIndices.forEach { index ->
            val value = input[index]
            val scalarActivation = 1.0f / (1.0f + exp(-value))
            activation[index] = scalarActivation
            derivativeActivation[index] = scalarActivation * (1.0f - scalarActivation)
        }
    }
}
