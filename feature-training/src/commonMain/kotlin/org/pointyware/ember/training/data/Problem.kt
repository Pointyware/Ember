package org.pointyware.ember.training.data

/**
 * Defines the set of supported problems for which data can be generated.
 */
sealed interface Problem {
    /**
     * The XOR problem presents two inputs, members of {0, 1}, and a single output which is
     * the result of binary xor between the two inputs interpreted.
     *
     * @param noise Shifts each input point with a uniform distribution scaled by this parameter.
     * @param setCount The number of sets of 4 examples. The total number will be `4*setCount`.
     */
    data class XorProblem(
        val noise: Float,
        val setCount: Int
    ): Problem

    /**
     * The Spiral problem presents two inputs, representing coordinates on an xy-plane, and
     * a single output which indicates membership to one of two arms.
     *
     * @param xMagnitude Determines the maximum magnitude of the x component.
     * @param yMagnitude Determines the maximum magnitude of the y component.
     */
    data class SpiralClassificationProblem(
        val xMagnitude: Float,
        val yMagnitude: Float
    ): Problem
}
