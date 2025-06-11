package org.pointyware.artes.scratch

import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Generates normally distributed random numbers using the Marsaglia polar method.
 * This method generates pairs of random numbers to ensure uniform distribution.
 */
object Marsaglia {

    private val generatedRandoms = mutableListOf<Double>()

    fun getNormal(): Double {
        if (generatedRandoms.isEmpty()) {
            generate(2)
        }
        return generatedRandoms.removeFirst()
    }

    fun generate(count: Int) {
        val iterations = (count + 1) / 2 // Generate pairs of random numbers
        (0 until iterations).forEach { _ ->
            while (true) {
                val u = Random.nextDouble(-1.0, 1.0)
                val v = Random.nextDouble(-1.0, 1.0)
                val s = u * u + v * v
                if (s < 1.0) {
                    val multiplier = sqrt(-2.0 * ln(s) / s)
                    generatedRandoms.add(u * multiplier)
                    generatedRandoms.add(v * multiplier)
                    break
                }
            }
        }
    }
}
