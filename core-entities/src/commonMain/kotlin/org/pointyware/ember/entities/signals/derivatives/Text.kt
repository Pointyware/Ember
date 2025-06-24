package org.pointyware.ember.entities.signals.derivatives

import org.pointyware.ember.entities.signals.Signal

/**
 * A sequence of symbols used to communicate concepts (whatever the hell those are).
 */
interface Text<C>: Signal.Derivative {
    val content: C
    val length: Int
}
