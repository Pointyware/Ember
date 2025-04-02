package org.pointyware.artes.data.hosts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.pointyware.artes.entities.hosts.Host

/**
 * Repository for managing hosts. Mediates between multiple data sources.
 *
 * Currently supported hosts:
 * - OpenAI
 */
interface HostRepository {
    suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String)
    suspend fun getHosts(): List<Host>
}

class HostRepositoryImpl(
    private val hostDao: HostDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val ioScope: CoroutineScope,
): HostRepository {

    override suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String) {
        hostDao.createHost(title, orgId, apiKey)
    }

    override suspend fun getHosts(): List<Host> {
        return hostDao.getAllHosts()
    }
}
