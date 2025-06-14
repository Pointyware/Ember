package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor

object MeanSquaredError : LossFunction {
    override fun compute(expected: Tensor, actual: Tensor): Double {
        require(actual.dimensions.size == expected.dimensions.size) {
            "Expected and actual tensors must have the same dimensions. %i != %i".format(
                actual.dimensions, expected.dimensions
            )
        }

        val diffs = Tensor(actual.dimensions).apply {
            mapEachIndexed { indices, _ ->
                actual[indices] - expected[indices]
            }
        }
        val squared = Tensor(actual.dimensions).apply {
            mapEachIndexed { indices, _ ->
                diffs[indices] * diffs[indices]
            }
        }

        val mean = squared.values.asSequence().average()

        return mean
    }
}
