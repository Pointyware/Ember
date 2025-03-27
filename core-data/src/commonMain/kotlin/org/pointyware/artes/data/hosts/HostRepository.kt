package org.pointyware.artes.data.hosts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Repository for managing hosts.
 *
 * Currently supported hosts:
 * - OpenAI
 */
interface HostRepository {
    suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String)
}

class HostRepositoryImpl(
    private val hostDao: HostDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val ioScope: CoroutineScope,
): HostRepository {

    override suspend fun createOpenAiHost(title: String, orgId: String, apiKey: String) {
        hostDao.createHost(title, orgId, apiKey)
    }
}
