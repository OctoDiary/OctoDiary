package org.bxkr.octodiary.domain.usecase.auth

import org.bxkr.octodiary.domain.model.auth.LogoutResult
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): LogoutResult {
        return authRepository.logout()
    }
}