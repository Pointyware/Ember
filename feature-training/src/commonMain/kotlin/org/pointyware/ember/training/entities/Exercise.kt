package org.pointyware.ember.training.entities

import org.pointyware.ember.entities.tensors.Tensor

/**
 * An individual training or test datum.
 */
data class Exercise(
    val input: Tensor,
    val output: Tensor,
)
