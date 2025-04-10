package org.pointyware.artes.entities.signals.primitives

import org.pointyware.artes.entities.signals.Signal

/**
 * Mechanoreceptors are responsible for a wide variety of senses
 *
 * Found below but their implementation needs to be considered:
 * 1. Tactile Corpuscle - skin pressure; rapidly adapting
 * 2. Pacinian Corpuscle - vibration; rapidly adapting
 * 3. Bulbous Corpuscle - kinesthetic sense/touch primarily in fingers; slowly adapting
 * 4. Muscle Spindle - detect muscular stretching and drive involuntary contraction of muscles; ? adaptation
 */
interface Mechanoreceptor<M>: Signal.Primitive {
    val mechanics: M

    /**
     * @param D deformation?
     */
    interface Proprioceptor<D>: Mechanoreceptor<D>

    /**
     * Senses fluid pressure (gas/liquid).
     */
    interface Baroreceptor: Signal.Primitive {
        val pressure: Float
    }

    interface TouchReceptor: Signal.Primitive
    interface VibrationReceptor: Signal.Primitive
    interface KinestheticReceptor: Signal.Primitive
    interface StretchReceptor: Signal.Primitive
}
