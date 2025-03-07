package org.pointyware.artes.core.entities.signals.primitives

import org.pointyware.artes.core.entities.Signal

/**
 * Detects osmotic pressure. Related to chemoreceptors but function more like mechanoreceptors:
 * changes is osmolality are detected through their structural effect on receptors.
 */
interface Osmoreceptor: Signal.Primitive {
    val pressure: Float
}
