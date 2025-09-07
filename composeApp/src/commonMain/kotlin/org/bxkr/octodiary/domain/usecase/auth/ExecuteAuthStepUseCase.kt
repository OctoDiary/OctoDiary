package org.bxkr.octodiary.domain.usecase.auth

import org.bxkr.octodiary.domain.model.Diary
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class ExecuteAuthStepUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        credentials: Credentials,
        diarySystem: Diary
    ) = authRepository.processAuthStep(
            credentials,
            diarySystem
        )
}