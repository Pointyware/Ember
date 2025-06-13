package org.pointyware.artes.scratch.activations

import kotlin.math.pow
import kotlin.math.tanh

/**
 * Calculates the hyperbolic tangent of the input value.
 */
object Tanh: ActivationFunction {
    override fun calculate(input: Double): Double {
        return tanh(input)
    }

    override fun derivative(input: Double): Double {
        return 1 - tanh(input).pow(2)
    }
}
