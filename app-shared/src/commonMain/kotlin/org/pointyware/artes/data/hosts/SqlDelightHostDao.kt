package org.pointyware.artes.data.hosts

import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.data.hosts.db.Hosts
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.services.openai.GeminiConfig
import org.pointyware.artes.services.openai.OpenAiConfig

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
            db.utilsQueries.lastId().executeAsOne()
        }
    }

    override fun createGeminiHost(title: String, apiKey: String): Long {
        return db.transactionWithResult {
            db.hostsQueries.insertHost(title, "", apiKey)
            db.utilsQueries.lastId().executeAsOne()
        }
    }

    private fun dtoToEntity(result: Hosts): HostConfig = when {
        result.orgId.isEmpty() -> GeminiConfig(
            id = result.id,
            title = result.name,
            apiKey = result.apiKey
        )
        else -> OpenAiConfig(
            id = result.id,
            title = result.name,
            orgId = result.orgId,
            apiKey = result.apiKey,
        )
    }

    override fun getHostById(id: Long): HostConfig {
        return db.hostsQueries.getHost(id).executeAsOne().let(::dtoToEntity)
    }

    override fun getAllHosts(): List<HostConfig> {
        return db.hostsQueries.getAllHosts().executeAsList().map(::dtoToEntity)
    }

    override fun updateHost(id: Long, title: String, orgId: String, apiKey: String) {
        db.hostsQueries.updateHost(title, orgId, apiKey, id)
    }

    override fun deleteHost(id: Long) {
        db.hostsQueries.deleteHost(id)
    }
}
