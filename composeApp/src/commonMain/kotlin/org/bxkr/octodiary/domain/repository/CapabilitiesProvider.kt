package org.bxkr.octodiary.domain.repository

import org.bxkr.octodiary.domain.model.diary.DiaryCapabilities
import org.bxkr.octodiary.domain.model.diary.DiaryId

interface CapabilitiesProvider {
    fun getCapabilities(diarySystemId: DiaryId): DiaryCapabilities
}