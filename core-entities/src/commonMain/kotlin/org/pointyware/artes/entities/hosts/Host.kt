package org.pointyware.artes.entities.hosts

/**
 *
 */
sealed interface Host {
    val id: Long
    val title: String
}

/**
 *
 */
class OpenAi(
    override val id: Long,
    override val title: String,
    val orgId: String,
    val apiKey: String,
): Host {

}
