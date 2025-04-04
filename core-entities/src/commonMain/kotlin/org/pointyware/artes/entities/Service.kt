package org.pointyware.artes.entities

/**
 * Describes an AI service. In the short-term, we will rely on established AI web services, so these
 * will be remote web services. In the long-term, we may want to support self-hosted services (e.g.
 * HuggingFace) and local services (e.g. Docker containers).
 */
sealed interface Service

interface RemoteService: Service
data object OpenAi: RemoteService
data object Google: RemoteService
data object Anthropic: RemoteService

/**
 * A user-hosted service running externally.
 */
data class SelfHosted(val url: String): RemoteService

/**
 * Executed on the same device.
 */
interface LocalService: Service
data object Docker: LocalService
