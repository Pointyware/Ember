package org.pointyware.ember.entities.signals.primitives

import org.pointyware.ember.entities.signals.Signal

/**
 * Senses temperature.
 */
interface Thermoreceptor: Signal.Primitive {
    val temperature: Float
}
