package org.pointyware.ember.evolution

import org.pointyware.ember.entities.networks.Network

/**
 * @param ribosomeSequence The sequence that determines the ribosome structure.
 * @param modelSequence The sequence that determines the model structure.
 */
class Genetics(
    val ribosomeSequence: ByteArray,
    val modelSequence: ByteArray
) {

}
