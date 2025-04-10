package org.pointyware.artes.entities

/**
 *
 */
interface Model {
    val id: Long
    val name: String
    val hostConfig: HostConfig
}

data class ModelImpl(
    override val id: Long,
    override val name: String,
    override val hostConfig: HostConfig,
) : Model
