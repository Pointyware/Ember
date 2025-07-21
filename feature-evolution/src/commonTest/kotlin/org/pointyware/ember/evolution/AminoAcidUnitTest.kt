package org.pointyware.ember.evolution

import kotlin.test.Test

class AminoAcidUnitTest {

    @Test
    fun verify_no_short_code_overlaps() {
        val confirmed = mutableSetOf<String>()
        AminoAcid.entries.forEach { aminoAcid ->
            val shortName = aminoAcid.shortCode
            assert(!confirmed.contains(shortName)) {
                "Duplicate short code: $shortName"
            }
            confirmed.add(shortName)
        }
    }
}
