package org.bxkr.octodiary.data.repository

import org.bxkr.octodiary.domain.model.diary.DiaryCapabilities
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.repository.CapabilitiesProvider
import org.koin.core.annotation.Single

@Single
class CapabilitiesProviderImpl : CapabilitiesProvider {
    private val defaultCapabilities = DiaryCapabilities()

    private val demoCapabilities = DiaryCapabilities(
        hasVisits = true,
        hasMeals = true,
        lessonAttendance = true
    )

    private val mesMosCapabilities = DiaryCapabilities(
        hasVisits = true,
        hasMeals = true,
        lessonAttendance = true
    )

    private val mesRegionalCapabilities = DiaryCapabilities(
        lessonAttendance = true
    )


    override fun getCapabilities(diarySystemId: DiaryId): DiaryCapabilities = when (diarySystemId) {
        DiaryId.Demo -> demoCapabilities
        DiaryId.MesMos -> mesMosCapabilities
        DiaryId.MesMosReg, DiaryId.MesKaluga -> mesRegionalCapabilities
        else -> defaultCapabilities
    }
}