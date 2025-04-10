package org.pointyware.artes.services.openai

import org.pointyware.artes.entities.Google
import org.pointyware.artes.entities.Host
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.OpenAi

/**
 * A user's configuration for OpenAI.
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

/**
 * A user's account configuration for Gemini.
 *
 * TODO: move to Google module
 */
class GeminiConfig(
    override val id: Long,
    override val title: String,
    val apiKey: String,
): HostConfig {

    override val host: Host
        get() = Google
}
