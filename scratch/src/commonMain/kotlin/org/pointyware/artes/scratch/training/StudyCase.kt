package org.pointyware.artes.scratch.training

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * An individual training datum.
 */
data class StudyCase(
    val input: Tensor,
    val output: Tensor,
)
