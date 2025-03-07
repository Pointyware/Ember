package org.pointyware.artes.core.entities.signals.primitives

import org.pointyware.artes.core.entities.Signal

/**
 * Senses temperature.
 */
interface Thermoreceptor: Signal.Primitive {
    val temperature: Float
}
