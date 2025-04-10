package org.pointyware.artes.entities.signals.derivatives

import org.pointyware.artes.entities.signals.Signal

/**
 * A collection of points spatially arranged on a plane that usually represent a projection into a
 * volume, but this often isn't the case, such as viewing diagrams or a web page.
 */
interface Image<S:Number>: Signal.Derivative {
    val width: Int
    val height: Int
    fun getPixel(x: Int, y: Int): S
}
