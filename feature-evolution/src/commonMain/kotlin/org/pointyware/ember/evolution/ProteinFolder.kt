package org.pointyware.ember.evolution

import org.pointyware.ember.entities.networks.Network

/**
 * Folders abstract away all the chemical and mechanical details of protein folding
 * for their respective dynamics.
 */
interface Folder<Output> {
    fun fold(polypeptides: List<List<AminoAcid>>): Output
}
