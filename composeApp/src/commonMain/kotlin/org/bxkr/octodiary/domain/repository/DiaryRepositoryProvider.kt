package org.bxkr.octodiary.domain.repository

import org.bxkr.octodiary.domain.model.diary.DiaryId

interface DiaryRepositoryProvider {
    fun getRepository(diarySystemId: DiaryId): DiaryRepository
}