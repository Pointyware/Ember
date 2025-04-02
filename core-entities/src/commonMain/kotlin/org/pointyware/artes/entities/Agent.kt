package org.pointyware.artes.entities

import org.pointyware.artes.entities.hosts.Host

/**
 *
 */
interface Agent {
    val id: String
    val name: String
    val service: Host
    val skills: Set<Skill>
}

/**
 *
 */
data class Model(
    val id: String
)
