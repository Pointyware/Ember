package org.pointyware.artes.core.entities.signals.derivatives

import org.pointyware.artes.core.entities.Signal

/**
 * A sequence of symbols used to communicate concepts (whatever the hell those are).
 */
interface Text<C>: Signal.Derivative {
    val content: C
    val length: Int
}
