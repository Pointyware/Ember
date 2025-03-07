package org.pointyware.artes.core.entities

/**
 * Describes an AI host/service.
 *
 * These are not necessarily remote services, but they will be
 * in the short-term.
 */
interface Service {
    val id: String
    val name: String
}

/**
 * Execution is managed by a hosted service (though this is not necessarily remote –just out of
 * the same process).
 */
interface HostedService: Service {
    val host: String
}

/**
 * Executed in the same process.
 */
interface LocalService: Service {}
