package org.pointyware.ember.evolution

import org.junit.Test
import kotlin.random.Random

class GeneticsUnitTest {

    @Test
    fun test_mutations() {
        val sequenceLength = 100
        val generations = 10

        val genetics = Genetics(
            ribosomeSequence = byteArrayOf(),
            modelSequence = ByteArray(sequenceLength) { Random.nextInt(0, 4).toByte() }
        )
        val ribosome = Ribosome(
            startCodon = 0,
            aminoAcidMap = { code ->
                val codeRemainder = code % 4
                when (codeRemainder) {
                    0 -> AminoAcid.Glycine
                    1 -> AminoAcid.GlutamicAcid
                    2 -> AminoAcid.AsparticAcid
                    3 -> AminoAcid.Alanine
                    else -> null
                }
            }
        )
        // Determine body plan for inputs/outputs
        /*
        Inputs:
          - Eye Spots x2: 2
          - Lips: 8
          - Feet: 8
        Outputs:
          - Legs x6: flex, extend: 12
          - Feet Grippers x6: flex, release: 12
         */
        val dynamics = ModelProteinDynamics(
            inputSize = 18,
            outputSize = 12
        )

        var currentGenetics = genetics
        repeat(generations) {
            println("Generation $it")
            println(currentGenetics)
            val polypeptides = ribosome.translateModel(genetics)
            polypeptides.forEach {
                println(it)
            }
            val model = dynamics.fold(polypeptides)
            println(model)
            currentGenetics = currentGenetics.mutate(0.1)
        }
    }
}
