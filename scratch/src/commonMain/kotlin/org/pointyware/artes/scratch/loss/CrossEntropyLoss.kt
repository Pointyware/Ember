package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor
import kotlin.math.ln

object CrossEntropyLoss : LossFunction {
    override fun compute(expected: Tensor, actual: Tensor): Double {
        val logProbs = Tensor.shape(*actual.dimensions).apply {
            mapEachIndexed { indices, _ ->
                // Adding a small constant to avoid log(0)
                -ln(actual[indices] + 1e-15) * expected[indices]
            }
        }

        val basis = actual.totalSize / actual.basisSize
        return logProbs.values.asSequence().sum() / basis
    }
}
