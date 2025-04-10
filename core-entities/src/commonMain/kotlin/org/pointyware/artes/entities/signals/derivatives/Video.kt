package org.pointyware.artes.entities.signals.derivatives

import org.pointyware.artes.entities.signals.Signal

/**
 * A series of related images, usually continuous, that depict visual information across time.
 */
interface Video: Signal.Derivative {
    val width: Int
    val height: Int
    // just for modeling; example of architecture disrupting efficiency
    fun getPixel(time: Float, x: Int, y: Int)
}
