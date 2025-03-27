package org.pointyware.artes.hosts.interactors

import org.pointyware.artes.data.hosts.HostRepository

/**
 *
 */
class CreateHostUseCase(
    private val hostRepository: HostRepository
) {

    suspend operator fun invoke(title: String, orgId: String, key: String): Result<Unit> = runCatching {
        hostRepository.createOpenAiHost(title, orgId, key)
    }
}
