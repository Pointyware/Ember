package org.pointyware.artes.interactors

import org.pointyware.artes.data.hosts.ServiceRepository

class RemoveServiceConfigUseCase(
    private val serviceConfigRepository: ServiceRepository,
) {

    suspend fun invoke(id: Long): Result<Unit> = runCatching {
        serviceConfigRepository.removeService(id)
    }
}
