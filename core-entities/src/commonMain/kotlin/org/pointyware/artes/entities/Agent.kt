package org.pointyware.artes.entities

/**
 *
 */
interface Agent {
    val id: Long
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
