package org.pointyware.ember.simulation

import org.pointyware.ember.entities.networks.Network
import org.pointyware.ember.entities.signals.Signal

/**
 * An embodied agent exists in some environment, which provides stimuli through [senses]
 * and can be acted upon through [actions].
 */
abstract class EmbodiedAgent(
    override val senses: List<Signal>,
    override val actions: List<Signal>,
): Agent {

}

/**
 * A neural embodied agent interacts with its environment through a [nervousSystem].
 */
class NeuralEmbodiedAgent(
    senses: List<Signal>,
    actions: List<Signal>,
    val nervousSystem: Network
): EmbodiedAgent(senses, actions) {

}

/**
 * A random embodied agent interacts with its environment through random actions.
 *
 * This can be useful for initially exploring some environment to provide
 * the first training data for a model without the overhead of running
 * a neural network.
 */
class RandomEmbodiedAgent(
    senses: List<Signal>,
    actions: List<Signal>,
): EmbodiedAgent(senses, actions) {

}
