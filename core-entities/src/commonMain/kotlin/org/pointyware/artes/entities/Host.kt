package org.pointyware.artes.entities

/**
 * Describes an AI service. In the short-term, we will rely on established AI web services, so these
 * will be remote web services. In the long-term, we may want to support self-hosted services (e.g.
 * HuggingFace) and local services (e.g. Docker containers).
 */
sealed interface Host

interface RemoteHost: Host
data object OpenAi: RemoteHost
data object Google: RemoteHost
data object Anthropic: RemoteHost

/**
 * A user-hosted service running externally.
 */
data class SelfHosted(val url: String): RemoteHost

/**
 * Executed on the same device.
 */
interface LocalHost: Host
data object Docker: LocalHost
