package org.pointyware.ember.simulation

import org.pointyware.ember.entities.networks.Network
import org.pointyware.ember.entities.signals.Signal

class NeuralAgent(
    override val senses: List<Signal>,
    override val actions: List<Signal>,
    val brain: Network
): Agent {
}
