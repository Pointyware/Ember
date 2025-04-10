package org.pointyware.artes.services.openai

import org.pointyware.artes.entities.Host
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.OpenAi

/**
 *
 */
class OpenAiConfig(
    override val id: Long,
    override val title: String,
    val orgId: String,
    val apiKey: String,
): HostConfig {

    override val host: Host
        get() = OpenAi
}
