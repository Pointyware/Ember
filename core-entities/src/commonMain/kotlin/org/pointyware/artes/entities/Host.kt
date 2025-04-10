package org.pointyware.artes.entities

/**
 * A registered/known instance of a [Service].
 *
 * TODO: rename to-
 *   ServiceHostConfig?
 *   ServiceHost?
 *   RegisteredHost?
 *   HostAccount?
 *   ServiceAccount?
 *   ServiceInstance?
 */
interface Host {
    val id: Long
    val title: String
}
