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

): Regularizer {

    override fun predict(
        input: Tensor,
        output: Tensor
    ) {
        val sumOfSquares = input.values.asSequence().sumOf { it.toDouble() * it }
        val rms = sqrt(sumOfSquares / input.totalSize + EPSILON).toFloat()

        output.mapEachFlatIndexed { index, _ -> input[index] / rms }
    }

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

        output.mapEachFlatIndexed { index, _ -> input[index] * rmsInverse }


    }

    override fun backward(
        error: Tensor,
        activation: Tensor,
        priorError: Tensor
    ) {
        TODO("Not yet implemented")
    }
}
