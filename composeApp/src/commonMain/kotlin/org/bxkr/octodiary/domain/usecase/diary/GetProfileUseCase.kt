package org.bxkr.octodiary.domain.usecase.diary

import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedException
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedType
import org.bxkr.octodiary.domain.repository.DiaryRepositoryProvider
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single

@Single
class GetProfileUseCase(
    private val diaryRepositoryProvider: DiaryRepositoryProvider,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = getRepository()?.getProfile() ?: Result.failure(
        NotAuthorizedException(NotAuthorizedType.SessionNotFound)
    )

    private suspend fun getRepository() =
        sessionRepository.getCurrentSession()?.diarySystemId?.let { diarySystemId ->
            diaryRepositoryProvider.getRepository(diarySystemId)
        }
}