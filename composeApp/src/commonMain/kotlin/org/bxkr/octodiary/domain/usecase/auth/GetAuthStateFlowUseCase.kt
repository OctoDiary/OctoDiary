package org.bxkr.octodiary.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class GetAuthStateFlowUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<AuthState> = authRepository.getAuthStateFlow()
}