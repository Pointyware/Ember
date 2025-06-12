package org.pointyware.artes.scratch.tensors

/**
 * A tensor that also stores gradient information during training.
 */
class LearningTensor(

): Tensor {

    override val dimensions: List<Int>
        get() = TODO("Not yet implemented")

    override fun set(index: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): Double {
        TODO("Not yet implemented")
    }

    override fun plus(other: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    override fun computeLoss(): Double {
        TODO("Not yet implemented")
    }

    companion object {
        /**
         * Creates a LearningTensor with random values.
         */
        fun random(vararg dimensions: Int): LearningTensor {
            TODO("Initialize all components with Gaussian randoms")
            return LearningTensor()
        }

        /**
         * Creates a LearningTensor initialized to zeros.
         */
        fun zeros(vararg dimensions: Int): LearningTensor {
            TODO("Initialize all components to zero")
            return LearningTensor()
        }
    }
}
