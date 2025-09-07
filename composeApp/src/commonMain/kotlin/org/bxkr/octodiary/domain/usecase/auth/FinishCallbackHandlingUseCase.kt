package org.bxkr.octodiary.domain.usecase.auth

import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class FinishCallbackHandlingUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.finishCallbackHandling()
    }
}