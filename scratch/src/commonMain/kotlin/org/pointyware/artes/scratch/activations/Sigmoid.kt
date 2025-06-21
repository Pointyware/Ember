package org.pointyware.artes.scratch.activations

import org.pointyware.artes.scratch.tensors.Tensor
import kotlin.math.exp

/**
 * Logistic function, also known as "the" sigmoid function; although,
 * a sigmoid function is any function that has an "S" shaped curve.
 */
object Sigmoid: ScalarActivationFunction {
    override fun scalarActivation(input: Double): Double {
        // Sigmoid function: f(x) = 1 / (1 + exp(-x))
        return 1.0 / (1.0 + exp(-input))
    }

    override fun scalarDerivative(input: Double): Double {
        // Derivative of sigmoid: f'(x) = f(x) * (1 - f(x))
        val sigmoidValue = scalarActivation(input)
        return sigmoidValue * (1.0 - sigmoidValue)
    }

    override fun calculate(input: Tensor, activation: Tensor, derivativeActivation: Tensor) {
        input.flatIndices.forEach { index ->
            val value = input[index]
            val scalarActivation = 1.0 / (1.0 + exp(-value))
            activation[index] = scalarActivation
            derivativeActivation[index] = scalarActivation * (1.0 - scalarActivation)
        }
    }
}
