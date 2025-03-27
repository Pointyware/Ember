package org.pointyware.artes.data.hosts

import org.pointyware.artes.entities.hosts.Host

interface HostDao {
    fun createHost(title: String, orgId: String, apiKey: String): Result<Long>
    fun getHostById(id: Long): Result<Host>
    fun getAllHosts(): Result<List<Host>>
    fun updateHost(id: Long, title: String, orgId: String, apiKey: String): Result<Unit>
    fun deleteHost(id: Long): Result<Unit>
}
