package org.pointyware.ember.entities.training

import org.pointyware.ember.entities.tensors.Tensor

/**
 * An individual training datum.
 */
data class StudyCase(
    val input: Tensor,
    val output: Tensor,
)
