package org.pointyware.ember.entities.training

import org.pointyware.ember.entities.tensors.Tensor

/**
 * An individual training or test datum.
 */
data class Exercise(
    val input: Tensor,
    val output: Tensor,
)
