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
     * Collects the weights of the model for an epoch.
     */
    data object Weights: Measurement()
    /**
     * Collects all biases of the model for an epoch.
     */
    data object AllParameters: Measurement()

    /**
     * Collects the weighted and biased inputs of the model, for each sample in the epoch. `z = Wx + b`
     */
    data object Preactivation: Measurement()

    /**
     * Collects the activations of the model, for each sample in the epoch. `a = f(z)`
     */
    data object Activation: Measurement()

    /**
     * Collects the derivatives of the model. `df/dz`
     */
    data object Derivative: Measurement()

    /**
     * Collects the loss, a.k.a. cost, of the model, averaged across an epoch. `∑C(a, y)/N`
     */
    data object Loss: Measurement()

    /**
     * Collects the accuracy of the model, averaged across an epoch. `∑A(a, y)/N`
     */
    data object Accuracy: Measurement()

    /**
     * Collects the gradients of the model, averaged across an epoch. `df/dw`
     */
    data object Gradient: Measurement()
}
