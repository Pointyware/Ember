package org.pointyware.ember.evolution

/**
 * These names are included for user convenience. They are listed in GACU order.
 * For example GGG, GGA, ..., ACA, ACC, ..., UUA, UUC, UUU.
 *
 * Image for quick reference of relation between human bases and amino acids:
 * https://upload.wikimedia.org/wikipedia/commons/d/d6/GeneticCode21-version-2.svg
 */
enum class AminoAcid(
    val shortCode: String,
) {
    Glycine("G"),
    GlutamicAcid("Ga"),
    AsparticAcid("A"),
    Alanine("Al"),
    Valine("V"),
    Arginine("Ar"),
    Serine("S"),
    Lysine("L"),
    Asparagine("As"),
    Threonine("Th"),
    Methionine("Me"),
    Isoleucine("Is"),
    // Arginine Duplicate Coding
    Glutamine("Gl"),
    Histidine("H"),
    Proline("P"),
    Leucine("Le"),
    Tryptophan("Tr"),
    // Stop Gap
    Cysteine("C"),
    // Stop Gap
    Tyrosine("Ty"),
    // Serine Duplicate Coding
    // Leucine Duplicate Coding
    Phenylalanine("Ph");
}
