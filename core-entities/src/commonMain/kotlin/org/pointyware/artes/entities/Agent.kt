package org.pointyware.artes.entities

/**
 *
 */
data class Agent(
    val id: Long,
    val name: String,
    val host: Host,
    val model: Model,
    val skills: Set<Skill>
)
