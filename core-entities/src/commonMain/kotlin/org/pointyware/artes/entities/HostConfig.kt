package org.pointyware.artes.entities

/**
 * A registered/known instance of a [Host].
 */
interface HostConfig {
    val id: Long
    val title: String
    val host: Host
}
