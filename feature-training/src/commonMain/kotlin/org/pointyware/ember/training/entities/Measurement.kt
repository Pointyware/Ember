package org.pointyware.ember.training.entities

/**
 * Measurements can be taken from just about any part of a system.
 *
 * They fall into three categories:
 * - Given information, such as model parameters, inputs, targets, etc.
 * - Intermediate information, a.k.a. calculations, such as pre-activations, activations, derivatives, loss, gradients, etc.
 * - Analytical information, a.k.a. calculations on calculations, such as
 */
sealed class Measurement(
//    val name: String
) {
    /**
     * Collects the parameters of the model for an epoch.
     *
     * Weights, biases,
     */
    data class Given<T: Any>(val label: String, val key: ComputationKey<T>): Measurement()

    /**
     * Collects intermediate information for an epoch.
     *
     * Pre-activations, activations, derivatives, loss, gradients, etc.
     */
    data class Intermediate<T: Any>(val label: String, val key: ComputationKey<T>): Measurement()

    /**
     * Collects analytical information for an epoch.
     *
     * Accuracy, shannon-entropy, mutual-information, etc.
     */
    data class Analytical<T: Any>(val label: String, val key: ComputationKey<T>): Measurement()

    /**
     * Collects the loss, a.k.a. cost, of the model, averaged across an epoch. `∑C(a, y)/N`
     */
    data object Loss: Measurement()
}
