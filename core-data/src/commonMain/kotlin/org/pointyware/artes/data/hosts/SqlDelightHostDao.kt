package org.pointyware.artes.data.hosts

import org.pointyware.artes.entities.hosts.Host

/**
 *
 */
class SqlDelightHostDao(
    // TODO: add SqlDelight database
): HostDao {
    override fun createHost(title: String, orgId: String, apiKey: String): Result<Long> {
        TODO("Not yet implemented")
    }

    override fun getHostById(id: Long): Result<Host> {
        TODO("Not yet implemented")
    }

    override fun getAllHosts(): Result<List<Host>> {
        TODO("Not yet implemented")
    }

    override fun updateHost(id: Long, title: String, orgId: String, apiKey: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteHost(id: Long): Result<Unit> {
        TODO("Not yet implemented")
    }
}
