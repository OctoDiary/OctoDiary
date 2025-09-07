package org.bxkr.octodiary.domain.usecase.auth

import org.bxkr.octodiary.domain.model.Diary
import org.bxkr.octodiary.domain.model.Region
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class GetAuthMethodsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(diary: Diary): List<AuthMethod> {
        return authRepository.getAuthMethods(diary)
    }
}