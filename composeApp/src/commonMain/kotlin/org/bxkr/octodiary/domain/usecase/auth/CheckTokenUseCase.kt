package org.bxkr.octodiary.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class CheckTokenUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: String, diaryId: DiaryId): Flow<TokenInfo> =
        authRepository.checkToken(token, diaryId)
}