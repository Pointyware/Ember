package org.pointyware.ember.evolution

import org.pointyware.ember.entities.activations.Logistic
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.entities.activations.Tanh
import org.pointyware.ember.entities.layers.DenseLayer
import org.pointyware.ember.entities.networks.Network
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.tensors.Tensor

/**
 * Naively translates polypeptide sequences into network layers with
 * parameters determined by genetics.
 *
 * @param inputSize The number of input neurons.
 * @param outputSize The number of output neurons.
 */
class ModelProteinDynamics(
    val inputSize: Int,
    val outputSize: Int
): Folder<Network> {

    private val numericAmines = setOf(
        // Weight Adjusters
        AminoAcid.Glycine,
        AminoAcid.GlutamicAcid,
        AminoAcid.AsparticAcid,
        AminoAcid.Alanine,
        // Bias Adjusters
        AminoAcid.Valine,
        AminoAcid.Arginine,
        // Output Adjusters
        AminoAcid.Cysteine,
        AminoAcid.Leucine,
    )
    private val functionalAmines = setOf(
        AminoAcid.Valine,
        AminoAcid.Arginine,
    )
    private val sensibleAmines = numericAmines + functionalAmines
    private val nonsenseAmines = AminoAcid.entries.toSet() - sensibleAmines

    override fun fold(polypeptides: List<List<AminoAcid>>): Network {
        var lastLayerSize = inputSize
        return SequentialNetwork(
            layers = polypeptides.map { chain ->
                val chainIterator = chain.iterator()

                var weightsMean = 0f
                var weightsStdDev = 0.1f
                var activationIndex = 0
                var outputSize = 8
                while (chainIterator.hasNext()) {
                    val aminoAcid = chainIterator.next()
                    when (aminoAcid) {
                        in numericAmines -> {
                            when (aminoAcid) {
                                AminoAcid.Cysteine -> {
                                    outputSize += 1
                                }
                                AminoAcid.Leucine -> {
                                    outputSize -= 1
                                }
                                AminoAcid.Valine -> {
                                    weightsStdDev += 0.1f
                                }
                                AminoAcid.Arginine -> {
                                    weightsStdDev -= 0.1f
                                }
                                AminoAcid.Glycine -> {
                                    weightsMean += 0.1f
                                }
                                AminoAcid.GlutamicAcid -> {
                                    weightsMean -= 0.1f
                                }
                                AminoAcid.AsparticAcid -> {
                                    weightsMean *= 1.1f
                                }
                                else -> { // AminoAcid.Alanine
                                    weightsMean *= 0.9f
                                }
                            }
                        }
                        in functionalAmines -> {
                            activationIndex = activationIndex + when (aminoAcid) {
                                AminoAcid.Valine -> 1
                                else -> 2 // AminoAcid.Arginine
                            } % 3
                        }
                        else -> {
                            // do nothing for nonsense amino acids?
                        }
                    }
                }

                if (outputSize < 4) throw IllegalArgumentException("No layer can have less than 4 neurons.")
                val weights = Tensor.random(
                    mean = weightsMean,
                    stdDev = weightsStdDev,
                    dimensions = intArrayOf(outputSize, lastLayerSize)
                )
                val biases = Tensor.zeros(outputSize, 1)
                val activationFunction = when (activationIndex) {
                    0 -> Logistic
                    1 -> Tanh
                    else -> ReLU
                }
                val layer = DenseLayer(
                    weights = weights,
                    biases = biases,
                    activationFunction = activationFunction
                )
                lastLayerSize = outputSize
                layer
            }
        )
    }
}
