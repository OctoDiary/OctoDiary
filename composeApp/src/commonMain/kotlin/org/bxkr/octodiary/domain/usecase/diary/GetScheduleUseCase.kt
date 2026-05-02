package org.bxkr.octodiary.domain.usecase.diary

import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedException
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedType
import org.bxkr.octodiary.domain.repository.DiaryRepositoryProvider
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single

@Single
class GetScheduleUseCase(
    private val diaryRepositoryProvider: DiaryRepositoryProvider,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(dateRange: LocalDateRange) = getRepository()?.getSchedule(dateRange) ?: Result.failure(
        NotAuthorizedException(NotAuthorizedType.SessionNotFound)
    )

    private suspend fun getRepository() =
        sessionRepository.getCurrentSession()?.diarySystemId?.let { diarySystemId ->
            diaryRepositoryProvider.getRepository(diarySystemId)
        }
}