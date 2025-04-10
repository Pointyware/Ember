package org.pointyware.artes.services

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.pointyware.artes.data.hosts.HostDao
import org.pointyware.artes.data.hosts.ServiceRepository
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.Model
import org.pointyware.artes.services.openai.OpenAiConfig

class ServiceRepositoryImpl(
    private val hostDao: HostDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val ioScope: CoroutineScope,
): ServiceRepository {

    override suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String) {
        hostDao.createHost(title, orgId, apiKey)
    }

    override suspend fun createGeminiHost(title: String, apiKey: String) {
        hostDao.createGeminiHost(title, apiKey)
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
        when (val hostConfig = hostDao.getHostById(hostId)) {
            is OpenAiConfig -> {
                TODO("Make models request using given configuration")
            }
            else -> {
                throw IllegalArgumentException("Unsupported host type: ${hostConfig::class.simpleName}")
            }
        }
    }
}
