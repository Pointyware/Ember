package org.pointyware.ember.training

import org.pointyware.ember.tensors.Tensor

/**
 * An individual training datum.
 */
data class StudyCase(
    val input: Tensor,
    val output: Tensor,
)
