package org.pointyware.ember.entities.regularizers

import org.pointyware.ember.entities.tensors.Tensor
import kotlin.math.pow
import kotlin.math.sqrt

private const val EPSILON = 1E-8f

/**
 * RmsNorm is typically used within a layer to normalize the activations from a previous
 * layer or sum of activations before calculating the preactivation weighted sum.
 *
 * Normalizers can also be seen as dedicated layers with an activation function that
 * acts on the whole instead of the parts, without weights or biases.
 */
class RmsNorm(
    val count: Int
): Regularizer {

    override fun predict(
        input: Tensor,
        output: Tensor
    ) {
        val sumOfSquares = input.values.asSequence().sumOf { it.toDouble() * it }
        val rms = sqrt(sumOfSquares / input.totalSize + EPSILON).toFloat()

        output.mapEachFlatIndexed { index, _ -> input[index] / rms }
    }

    private val jacobian = Tensor.zeros(count, count)
    override fun forward(
        input: Tensor,
        output: Tensor,
        derivative: Tensor
    ) {
        val n = input.totalSize.toFloat()
        val nInverse = 1f / n

        val sumOfSquares = input.values.asSequence().sumOf { it.toDouble() * it }
        val rmsSquared = sumOfSquares * nInverse + EPSILON
        val rms = sqrt(rmsSquared).toFloat()
        val rmsInverse = 1f / rms

        val jacobianDenominator = nInverse * rmsInverse.pow(3)
        val nRmsSquared = n * rmsSquared
        for (i in 0 until count) {
            val xi = input.data[i]
            for (j in 0 until count) {
                jacobian[i, j] = if (i == j) {
                    (xi * xi + nRmsSquared) * jacobianDenominator
                } else {
                    val xj = input.data[j]
                    (xi * xj) * jacobianDenominator
                }.toFloat()
            }
        }

        output.mapEachFlatIndexed { index, _ -> input[index] * rmsInverse }

        derivative.assign(jacobian)
    }

    override fun backward(
        error: Tensor,
        priorActivation: Tensor,
        priorActivationDerivative: Tensor,
        priorError: Tensor
    ) {
        // when calculating normDerivative, it is a Jacobian matrix. To find
        //   input layer error from normalizing layer, multiply error attributed to
        //   containing layer into transpose of jacobian to find error attributed
        //   to normalizing layer, which can then be treated the same as other
        //   errors propagated from simple, dense layers.
        priorError.assign(
            priorActivationDerivative *
                    jacobian.transpose().matrixMultiply(error)
        )
    }
}
