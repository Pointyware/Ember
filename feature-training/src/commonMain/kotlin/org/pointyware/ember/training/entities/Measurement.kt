package org.pointyware.ember.training.entities

/**
 * Measurements can be taken from just about any part of a system.
 *
 * They fall into three categories:
 * - Given information, such as model parameters, inputs, targets, etc.
 * - Intermediate information, a.k.a. calculations, such as pre-activations, activations, derivatives, loss, gradients, etc.
 * - Analytical information, a.k.a. calculations on calculations, such as
 */
data class Measurement(
    val name: String,
    val unit: Subject
) {
    enum class Subject {
        Error,
        Accuracy,
        Gradient,
    }
}

/**
 * TODO: replace current Measurement with this one
 */
sealed interface Measurement2 {
    /**
     *
     */
    data object Error: Measurement2

    /**
     *
     */
    data object Accuracy: Measurement2

    /**
     *
     */
    data object Gradient: Measurement2

    /**
     *
     */
    class Tensor(val dimensions: IntArray)
}
