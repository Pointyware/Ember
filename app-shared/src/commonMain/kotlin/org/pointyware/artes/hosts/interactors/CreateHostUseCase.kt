package org.pointyware.artes.hosts.interactors

import org.pointyware.artes.data.hosts.ServiceRepository

/**
 *
 */
class CreateHostUseCase(
    private val serviceRepository: ServiceRepository
) {

    suspend operator fun invoke(title: String, orgId: String, key: String): Result<Unit> = runCatching {
        serviceRepository.createOpenAiHost(title, orgId, key)
    }
}
