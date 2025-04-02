package org.pointyware.artes.entities

/**
 *
 */
interface Agent {
    val id: Long
    val name: String
    val host: Host
    val skills: Set<Skill>
}

/**
 *
 */
data class Model(
    val id: String
)
