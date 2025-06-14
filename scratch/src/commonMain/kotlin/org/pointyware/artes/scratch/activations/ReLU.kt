package org.pointyware.artes.scratch.activations

/**
 * Rectified Linear Unit (ReLU) activation function.
 */
object ReLU: ScalarActivationFunction {
    override fun scalarActivation(input: Double): Double {
        return if (input > 0) input else 0.0
    }

    override fun scalarDerivative(input: Double): Double {
        return if (input > 0) 1.0 else 0.0
    }
}
