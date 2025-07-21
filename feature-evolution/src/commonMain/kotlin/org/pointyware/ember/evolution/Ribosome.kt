package org.pointyware.ember.evolution

/**
 * The ribosome does the heavy-lifting.
 *
 * // TODO: depend on Genetics to define bases count and code size?
 */
class Ribosome(
    val startCodon: Int,
    val aminoAcidMap: Map<Int, AminoAcid?>
) {

    /**
     * Translates the given [Genetics] into a [Ribosome] using this
     * [Ribosome]'s [aminoAcidMap].
     */
    fun translateRibosome(genetics: Genetics): Ribosome {
        // TODO: experiment with creating aminoAcidMap from genetics.ribosomeSequence
        return Ribosome(startCodon, aminoAcidMap)
    }
    /**
     * Naively begins at the beginning of the given [Genetics.modelSequence], looking
     * for the first [startCodon].
     */
    fun translateModel(genetics: Genetics): List<List<AminoAcid>> {
        var position = 0
        val finalPosition = genetics.modelSequence.size - codonSize
        val proteins = mutableListOf<List<AminoAcid>>()

        var currentProtein: MutableList<AminoAcid>? = null
        while (position < finalPosition) {
            // 1. Calculate codon at current window
            val codon = toCodeIndex(genetics.modelSequence, position)
            // If codon is start codon, initiate protein sequence and skip forward to the next
            if (codon == startCodon) {
                // Start coding proteins
                currentProtein = mutableListOf()
                // Advance to first translated amino acid
                position += codonSize
            } else if (currentProtein != null) {
                // Translate codon to amino acid
                aminoAcidMap[codon]?.let {
                    // Append to current sequence
                    currentProtein.add(it)
                } ?: run { // Null amino acid => Stop codon
                    // Release new sequence
                    proteins.add(currentProtein.toList())
                    currentProtein = null
                }
                position += codonSize
            } else { // Not start codon and no active protein sequence; slide window by 1
                position++
            }
        }

        return proteins.toList()
    }

    companion object {
        const val baseCount: Int = 4
        const val codonSize = 3

        fun fromCodeIndex(index: Int): ByteArray {
            val output = ByteArray(codonSize)
            fromCodeIndex(index, output, 0)
            return output
        }
        fun fromCodeIndex(index: Int, output: ByteArray, offset: Int) {
            var remainder = index
            for (p in codonSize - 1 downTo 0) {
                output[offset + p] = (remainder % baseCount).toByte()
                remainder /= baseCount
            }
        }

        fun toCodeIndex(sequence: ByteArray, offset: Int): Int {
            var codon = 0
            for (p in 0 until codonSize) {
                // shift left
                codon *= baseCount
                // add digit
                codon += sequence[offset + p]
            }
            return codon
        }
    }
}
