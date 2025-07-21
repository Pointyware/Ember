package org.pointyware.ember.evolution

import kotlin.random.Random

/**
 * @param ribosomeSequence The sequence that determines the ribosome structure.
 * @param modelSequence The sequence that determines the model structure.
 */
class Genetics(
    val ribosomeSequence: ByteArray,
    val modelSequence: ByteArray
) {
    private val entropy = Random.Default

    /**
     *
     */
    fun mutate(mutationRate: Double): Genetics {
        return Genetics(
            ribosomeSequence = ribosomeSequence, // TODO: mutate once of interest
            modelSequence = mutateBytes(modelSequence, mutationRate)
        )
    }

    private val insertionError = 1.0
    private val deletionError = 1.0
    private val substitutionError = 2.0
    private val mutationError = insertionError + deletionError + substitutionError

    private fun mutateBytes(sequence: ByteArray, mutationRate: Double): ByteArray {
        val mutationCount = (sequence.size * mutationRate).toInt()

        val newSequence = sequence.toMutableList()
        for (i in 0 until mutationCount) {
            val mutationType = entropy.nextDouble(mutationError)
            val index = entropy.nextInt(0, sequence.size)
            when {
                mutationType < insertionError -> {
                    newSequence.add(index, entropy.nextInt(from = 0, until = 4).toByte())
                }
                mutationType < deletionError -> {
                    newSequence.removeAt(index)
                }
                else -> { // mutation < substitutionError
                    newSequence[index] = entropy.nextInt(from = 0, until = 4).toByte()
                }
            }
        }
        return newSequence.toByteArray()
    }

    override fun toString(): String {
        // Map byte arrays to GACU symbols
        val modelString = modelSequence.joinToString(separator = "") {
            when (it) {
                0.toByte() -> "G"
                1.toByte() -> "A"
                2.toByte() -> "C"
                else -> "U"
            }
        }
        return "Genetics(modelSequence[${modelSequence.size}]='$modelString')"
    }

    companion object {
        const val baseCount: Int = 4
        const val codonSize = 3
        const val deletionByte =  0x001000.toByte()
        const val insertionByte = 0x011000.toByte()
    }
}
