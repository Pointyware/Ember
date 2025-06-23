package org.pointyware.ember.activations

import kotlin.math.pow
import kotlin.math.tanh

/**
 * Calculates the hyperbolic tangent of the input value.
 */
object Tanh: ScalarActivationFunction {
    override fun scalarActivation(input: Double): Double {
        return tanh(input)
    }

    override fun scalarDerivative(input: Double): Double {
        return 1 - tanh(input).pow(2)
    }
}
