package org.pointyware.ember.entities.activations

import kotlin.math.pow
import kotlin.math.tanh

/**
 * Calculates the hyperbolic tangent of the input value.
 */
object Tanh: ScalarActivationFunction {
    override val parameterCount: Int
        get() = 0

    override fun scalarActivation(input: Float): Float {
        return tanh(input)
    }

    override fun scalarDerivative(input: Float): Float {
        return 1 - tanh(input).pow(2)
    }
}
