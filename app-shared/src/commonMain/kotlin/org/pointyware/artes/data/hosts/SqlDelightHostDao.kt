package org.pointyware.artes.data.hosts

import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.data.hosts.db.Hosts
import org.pointyware.artes.entities.hosts.Host
import org.pointyware.artes.services.openai.OpenAi

/**
 *
 */
class SqlDelightHostDao(
    lazyDb: Lazy<HostDb>,
): HostDao {

    private val db: HostDb by lazyDb

    override fun createHost(title: String, orgId: String, apiKey: String): Long {
        return db.transactionWithResult {
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

    override fun getHostById(id: Long): Host {
        return db.hostsQueries.getHost(id).executeAsOne().let(::dtoToEntity)
    }

    override fun getAllHosts(): List<Host> {
        return db.hostsQueries.getAllHosts().executeAsList().map(::dtoToEntity)
    }

    override fun updateHost(id: Long, title: String, orgId: String, apiKey: String) {
        db.hostsQueries.updateHost(title, orgId, apiKey, id)
    }

    override fun deleteHost(id: Long) {
        db.hostsQueries.deleteHost(id)
    }
}
