package org.pointyware.ember.entities.activations

/**
 * Rectified Linear Unit (ReLU) activation function.
 */
object ReLU: ScalarActivationFunction {
    override fun scalarActivation(input: Float): Float {
        return if (input > 0) input else 0.0f
    }

    override fun scalarDerivative(input: Float): Float {
        return if (input > 0) 1.0f else 0.0f
    }
}
