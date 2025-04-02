package org.pointyware.artes.interactors.hosts

import org.pointyware.artes.data.hosts.HostRepository
import org.pointyware.artes.entities.Host

/**
 */
class GetHostServicesUseCase(
    private val hostRepository: HostRepository
) {

    suspend operator fun invoke(): Result<List<Host>> = runCatching {
        hostRepository.getHosts()
    }
}
