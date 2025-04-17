package org.pointyware.artes.services

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.pointyware.artes.data.hosts.HostDao
import org.pointyware.artes.data.hosts.ServiceRepository
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.entities.Model
import org.pointyware.artes.entities.ModelImpl
import org.pointyware.artes.services.openai.OpenAiConfig
import org.pointyware.artes.services.openai.network.OpenAiModelFetcher

class ServiceRepositoryImpl(
    private val hostDao: HostDao,
    private val openAiModelFetcher: OpenAiModelFetcher,
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
        return when (val hostConfig = hostDao.getHostById(hostId)) {
            is OpenAiConfig -> {
                openAiModelFetcher.invoke(hostConfig)
                    .map {
                        ModelImpl(
                            id = it.created,
                            name = it.id,
                            hostConfig = hostConfig
                        )
                    }
            }
            else -> {
                throw IllegalArgumentException("Unsupported host type: ${hostConfig::class.simpleName}")
            }
        }
    }

    override suspend fun removeService(id: Long) {
        TODO("Not yet implemented")
    }
}
