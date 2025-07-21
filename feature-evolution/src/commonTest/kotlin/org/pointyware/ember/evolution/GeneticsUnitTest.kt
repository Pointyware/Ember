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
            aminoAcidMap = mapOf(
                0 to AminoAcid.Glycine,
                1 to AminoAcid.GlutamicAcid,
                2 to AminoAcid.AsparticAcid,
                3 to AminoAcid.Alanine
            )
        )
        val dynamics = ModelProteinDynamics()

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
