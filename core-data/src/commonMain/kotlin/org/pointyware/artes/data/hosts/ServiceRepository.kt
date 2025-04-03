package org.pointyware.artes.data.hosts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.pointyware.artes.entities.Host

/**
 * Repository for managing hosts. Mediates between multiple data sources.
 *
 * Currently supported hosts:
 * - OpenAI
 */
interface ServiceRepository {
    suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String)
    suspend fun getHosts(): List<Host>
    suspend fun getService(id: Long): Host
}

class ServiceRepositoryImpl(
    private val hostDao: HostDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val ioScope: CoroutineScope,
): ServiceRepository {

    override suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String) {
        hostDao.createHost(title, orgId, apiKey)
    }

    override suspend fun getHosts(): List<Host> {
        return hostDao.getAllHosts()
    }

    override suspend fun getService(id: Long): Host {
        TODO("Not yet implemented")
    }
}
