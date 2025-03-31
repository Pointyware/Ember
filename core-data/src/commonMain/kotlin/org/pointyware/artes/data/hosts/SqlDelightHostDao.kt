package org.pointyware.artes.data.hosts

import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.data.hosts.db.Hosts
import org.pointyware.artes.entities.hosts.Host
import org.pointyware.artes.entities.hosts.OpenAi

/**
 *
 */
class SqlDelightHostDao(
    lazyDb: Lazy<HostDb>,
): HostDao {

    private val db: HostDb by lazyDb

    override fun createHost(title: String, orgId: String, apiKey: String): Result<Long> = runCatching {
        db.transactionWithResult {
            db.hostsQueries.insertHost(title, orgId, apiKey)
            db.hostsQueries.lastId().executeAsOne()
        }
    }

    private fun dtoToEntity(result: Hosts): Host = OpenAi(
        id = result.id,
        title = result.name,
        orgId = result.orgId,
        apiKey = result.apiKey,
    )

    override fun getHostById(id: Long): Result<Host> = runCatching {
        db.hostsQueries.getHost(id).executeAsOne().let(::dtoToEntity)
    }

    override fun getAllHosts(): Result<List<Host>> = runCatching {
        db.hostsQueries.getAllHosts().executeAsList().map(::dtoToEntity)
    }

    override fun updateHost(id: Long, title: String, orgId: String, apiKey: String): Result<Unit> = runCatching {
        db.hostsQueries.updateHost(title, orgId, apiKey, id)
    }

    override fun deleteHost(id: Long): Result<Unit> = runCatching {
        db.hostsQueries.deleteHost(id)
    }
}
