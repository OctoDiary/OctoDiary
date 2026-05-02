package org.bxkr.octodiary.domain.usecase.diary

import org.bxkr.octodiary.domain.repository.CapabilitiesProvider
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single


@Single
class GetCapabilitiesUseCase(
    private val capabilitiesProvider: CapabilitiesProvider,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = getCapabilities()
        ?: throw IllegalStateException("Do not invoke GetCapabilitiesUseCase when not authorized")

    private suspend fun getCapabilities() =
        sessionRepository.getCurrentSession()?.diarySystemId?.let { diarySystemId ->
            capabilitiesProvider.getCapabilities(diarySystemId)
        }
}