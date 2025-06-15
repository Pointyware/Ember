package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor
import kotlin.math.ln

object CrossEntropyLoss : LossFunction {
    override fun compute(expected: Tensor, actual: Tensor): Double {
        val logProbs = Tensor.shape(*actual.dimensions).mapEachFlatIndexed { index, _ ->
            -ln(actual[index] + 1e-15) * expected[index]
        }

        return logProbs.values.asSequence().sum() / actual.area
    }
}
