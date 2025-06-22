package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Computes the mean squared error (MSE) between the expected and actual tensors.
 */
object MeanSquaredError : LossFunction {
    override fun compute(expected: Tensor, actual: Tensor): Double {
        require(actual.dimensions.size == expected.dimensions.size) {
            "Expected and actual tensors must have the same dimensions. %i != %i".format(
                actual.dimensions, expected.dimensions
            )
        }

        val diffs = Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            actual[index] - expected[index]
        }
        val squared = Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            diffs[index] * diffs[index]
        }

        return squared.values.asSequence().sum()
    }

    override fun derivative(expected: Tensor, actual: Tensor): Tensor {
        return Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            2 * (actual[index] - expected[index])
        }
    }

    override fun computeAndDerivative(
        expected: Tensor,
        actual: Tensor,
        derivative: Tensor
    ): Double {
        require(actual.dimensions.size == expected.dimensions.size) {
            "Expected and actual tensors must have the same dimensions. %i != %i".format(
                actual.dimensions, expected.dimensions
            )
        }

        val diffs = Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            actual[index] - expected[index]
        }
        val squared = Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            diffs[index] * diffs[index]
        }

        val factor = 2.0 / actual.area
        derivative.mapEachFlatIndexed { index, _ -> diffs[index] * factor }

        return squared.values.asSequence().average()
    }
}
