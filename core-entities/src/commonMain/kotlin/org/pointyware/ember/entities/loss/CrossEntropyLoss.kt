package org.pointyware.ember.entities.loss

import org.pointyware.ember.entities.tensors.Tensor
import kotlin.math.ln


/**
 * Also known as pseudo-count in population statistics.
 */
private const val EPSILON = 1e-15f

/**
 * Cross-entropy loss function.
 */
object CrossEntropyLoss : LossFunction {
    override fun compute(expected: Tensor, actual: Tensor): Double {
        val logProbs = Tensor.zeros(*actual.dimensions).mapEachFlatIndexed { index, _ ->
            -ln(actual[index] + EPSILON) * expected[index]
        }

        return logProbs.values.asSequence().sum() / actual.area.toDouble()
    }

    override fun derivative(expected: Tensor, actual: Tensor): Tensor {
        require(actual.dimensions.size == expected.dimensions.size) {
            "Expected and actual tensors must have the same dimensions. %i != %i".format(
                actual.dimensions, expected.dimensions
            )
        }

        return Tensor(actual.dimensions).mapEachFlatIndexed { index, _ ->
            -expected[index] / (actual[index] + EPSILON)
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

        val logProbs = Tensor.zeros(*actual.dimensions).mapEachFlatIndexed { index, _ ->
            derivative[index] = -expected[index] / (actual[index] + EPSILON)
            -ln(actual[index] + EPSILON) * expected[index]
        }

        return logProbs.values.asSequence().sum() / actual.area.toDouble()
    }
}
