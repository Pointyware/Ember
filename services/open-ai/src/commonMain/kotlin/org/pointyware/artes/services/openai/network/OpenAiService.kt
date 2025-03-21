package org.pointyware.artes.services.openai.network

import org.pointyware.artes.core.entities.HostedService

/**
 */
data class OpenAiService(
    override val id: String,
    override val name: String,
    override val host: String,
    val credentials: OpenAiCredentials,
): HostedService
