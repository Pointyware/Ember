package org.pointyware.artes.entities

/**
 * Describes an AI host/service.
 *
 * These are not necessarily remote services, but they will be
 * in the short-term.
 */
sealed interface Service

/**
 * Execution is managed by a hosted service (though this is not necessarily remote –just out of
 * the same process).
 */
interface RemoteService: Service
data object OpenAi: RemoteService
data object Google: RemoteService
data object Anthropic: RemoteService

/**
 * A user-hosted service running externally. TODO: support
 */
data class SelfHosted(val url: String): RemoteService

/**
 * Executed on the same device.
 */
interface LocalService: Service
data object Docker: LocalService
