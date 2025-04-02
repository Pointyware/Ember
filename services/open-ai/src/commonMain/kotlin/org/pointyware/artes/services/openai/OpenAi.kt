package org.pointyware.artes.services.openai

import org.pointyware.artes.entities.hosts.Host

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
