package org.pointyware.ember.entities.signals.derivatives

import org.pointyware.ember.entities.signals.Signal

/**
 * A sequence of signals corresponding to pressure waves.
 */
interface Audio<S:Number>: Signal.Derivative {
    /**
     * The individual pressure signals.
     */
    val waveform: Array<S>

    /**
     * The number of samples (signals) per second.
     */
    val sampleRate: Float
}
