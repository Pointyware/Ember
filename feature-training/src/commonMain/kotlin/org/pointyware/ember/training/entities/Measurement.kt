package org.pointyware.ember.training.entities

/**
 * Measurements can be taken from just about any part of a system.
 *
 * They fall into three categories:
 * - Given information, such as model parameters, inputs, targets, etc.
 * - Intermediate information, a.k.a. calculations, such as pre-activations, activations, derivatives, loss, gradients, etc.
 * - Analytical information, a.k.a. calculations on calculations, such as
 */
sealed class Measurement<T: Any>(
//    val name: String
    val key: ComputationKey<T>
) {
    /**
     * Collects the parameters of the model for an epoch.
     *
     * Weights, biases,
     */
    class Given<T: Any>(val label: String, key: ComputationKey<T>): Measurement<T>(key)

    /**
     * Collects intermediate information for an epoch.
     *
     * Pre-activations, activations, derivatives, loss, gradients, etc.
     */
    class Intermediate<T: Any>(val label: String, key: ComputationKey<T>): Measurement<T>(key)

    /**
     * Collects analytical information for an epoch.
     *
     * Accuracy, shannon-entropy, mutual-information, etc.
     */
    class Analytical<T: Any>(val label: String, key: ComputationKey<T>): Measurement<T>(key)
}
