package org.pointyware.artes.data.hosts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.Model

/**
 * Repository for managing hosts. Mediates between multiple data sources.
 *
 * Currently supported hosts:
 * - OpenAI
 */
interface ServiceRepository {
    suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String)
    suspend fun getHosts(): List<HostConfig>
    suspend fun getService(id: Long): HostConfig
    suspend fun getModel(id: Long): Model
    suspend fun getModels(hostId: Long): List<Model>
}

class ServiceRepositoryImpl(
    private val hostDao: HostDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val ioScope: CoroutineScope,
): ServiceRepository {

    override suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String) {
        hostDao.createHost(title, orgId, apiKey)
    }

    override suspend fun getHosts(): List<HostConfig> {
        return hostDao.getAllHosts()
    }

    override suspend fun getService(id: Long): HostConfig {
        TODO("Not yet implemented")
    }

    override suspend fun getModel(id: Long): Model {
        TODO("Not yet implemented")
    }

    override suspend fun getModels(hostId: Long): List<Model> {
        TODO("Not yet implemented")
    }
}
