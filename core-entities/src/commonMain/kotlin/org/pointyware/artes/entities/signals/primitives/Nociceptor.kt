package org.pointyware.artes.entities.signals.primitives

import org.pointyware.artes.entities.signals.Signal

/**
 * I'm not sure how this would be implemented in practice. Nociceptors can be polymodal, responding
 * to a variety of stimuli, only firing an action potential when some threshold is reached for a
 * given stimulus.
 *
 * Constraining ourselves to direct analogs of organic systems could be a bad approach.
 *
 * I would expect that nociceptors developed as semi-sacrificial cells that could be reconstructed
 * more easily than primary sensory cells in addition to being more sensitive – or maybe sensitivity
 * and fragility go hand-in-hand. Why would primary sensory cells not simply trigger noxious stimuli
 * above some threshold? The fact that the sensory portion of nociceptors are comprised of "free
 * ends" likely makes them cheaper, faster, and easier to reconstruct if a destructive threshold is
 * crossed. If the associated primary sense is lost, while that system recovers, the damaging
 * threshold can still be sensed to prevent further damage. Further reading needed.
 *
 * In conclusion (for now), we can probably get away with setting thresholds for the primary
 * receptors that trigger an additional noxious signal. Maybe for training's sake, we can attach
 * nociceptors to the output of primary receptors to allow back/ff-prop to find the appropriate
 * weight/bias, but these would probably be implemented as some fixed threshold known to be
 * damaging to a given system, e.g. strain sensors in artificial skin.
 *
 * 1. Thermal
 * 2. Mechanical
 * 3. Chemical
 * 4. Sleeping
 * 5. Polymodal
 *
 * Note: not all "free nerve endings" are nociceptors.
 *
 * Source: https://en.wikipedia.org/wiki/Nociceptor
 */
interface Nociceptor<T>: Signal.Primitive {
    val threat: T
}
