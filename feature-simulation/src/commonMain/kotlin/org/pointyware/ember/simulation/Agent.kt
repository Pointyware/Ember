package org.pointyware.ember.simulation

import org.pointyware.ember.entities.signals.Signal

/**
 * Agents can exist at any scale of the system. Their body plan determines how they
 * interact with an environment, including how collisions stimulate different
 * senses, and how their actions affect the environment.
 *
 * For each time step of the simulation, each input to the senses is updated,
 * followed by an internal processing model, which determines the action outputs,
 * innervating the body, which acts on the environment.
 *
 * TODO: support Player Character agents or create a separate interface for
 *   players to navigate the environment.
 */
interface Agent {
    val senses: List<Signal>
    val actions: List<Signal>
}
