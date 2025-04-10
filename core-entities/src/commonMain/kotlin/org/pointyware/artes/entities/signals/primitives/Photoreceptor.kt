package org.pointyware.artes.entities.signals.primitives

import org.pointyware.artes.entities.signals.Signal

/**
 * Senses light a.k.a. electromagnetic radiation (in near visible spectrum).
 *
 * Considering Radioreceptor to detect signals outside the visible spectrum.
 */
interface Photoreceptor<S>: Signal.Primitive {
    val output: S
}
