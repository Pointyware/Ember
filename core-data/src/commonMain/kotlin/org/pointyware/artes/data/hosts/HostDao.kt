package org.pointyware.artes.data.hosts

import org.pointyware.artes.entities.Host

interface HostDao {
    fun createHost(title: String, orgId: String, apiKey: String): Long
    fun getHostById(id: Long): Host
    fun getAllHosts(): List<Host>
    fun updateHost(id: Long, title: String, orgId: String, apiKey: String)
    fun deleteHost(id: Long)
}
