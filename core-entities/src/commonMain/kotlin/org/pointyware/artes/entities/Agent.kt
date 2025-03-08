package org.pointyware.artes.entities

/**
 *
 */
interface Agent {
    val id: String
    val name: String
    val service: Service
    val skills: Set<Skill>
}

/**
 *
 */
data class Model(
    val id: String
)
