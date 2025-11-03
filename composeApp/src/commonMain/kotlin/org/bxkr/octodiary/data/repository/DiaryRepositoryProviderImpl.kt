package org.bxkr.octodiary.data.repository

import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.repository.DiaryRepository
import org.bxkr.octodiary.domain.repository.DiaryRepositoryProvider
import org.koin.core.annotation.Single

@Single
class DiaryRepositoryProviderImpl(
    private val repositories: List<DiaryRepository>
) : DiaryRepositoryProvider {
    override fun getRepository(diarySystemId: DiaryId): DiaryRepository =
        repositories.first { it.responsibleFor == diarySystemId }
}