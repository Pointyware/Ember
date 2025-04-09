package org.pointyware.artes.interactors.hosts

import org.pointyware.artes.data.hosts.ServiceRepository
import org.pointyware.artes.entities.Host

/**
 * Retrieves a list of registered/configured service host accounts.
 */
class GetHostServicesUseCase(
    private val serviceRepository: ServiceRepository
) {

    suspend operator fun invoke(): Result<List<Host>> = runCatching {
        serviceRepository.getHosts()
    }
}
