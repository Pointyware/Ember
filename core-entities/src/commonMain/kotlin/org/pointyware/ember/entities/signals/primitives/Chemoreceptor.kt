package org.pointyware.ember.entities.signals.primitives

import org.pointyware.ember.entities.signals.Signal

/**
 * There are a massive variety of chemoreceptors. In general they sense chemical concentrations.
 * Specifically, they are responsible for sense of smell and taste as well as the ability to sense
 * blood
 */
interface Chemoreceptor<C>: Signal.Primitive {
    val chemicals: C
}
