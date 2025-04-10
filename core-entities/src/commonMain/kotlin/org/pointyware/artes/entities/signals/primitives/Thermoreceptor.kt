package org.pointyware.artes.entities.signals.primitives

import org.pointyware.artes.entities.signals.Signal

/**
 * Senses temperature.
 */
interface Thermoreceptor: Signal.Primitive {
    val temperature: Float
}
