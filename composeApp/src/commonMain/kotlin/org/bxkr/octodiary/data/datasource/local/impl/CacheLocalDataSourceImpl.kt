package org.bxkr.octodiary.data.datasource.local.impl

import io.github.xxfast.kstore.KStore
import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.di.annotation.HomeworkEntriesCache
import org.bxkr.octodiary.di.annotation.ProfileCache
import org.bxkr.octodiary.di.annotation.ScheduleCache
import org.bxkr.octodiary.domain.model.cache.CacheStorage
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.koin.core.annotation.Single

@Single
class CacheLocalDataSourceImpl(
    @param:ProfileCache private val profileStore: KStore<CacheStorage.ProfileStorage>,
    @param:ScheduleCache private val scheduleStore: KStore<CacheStorage.ScheduleStorage>,
    @param:HomeworkEntriesCache private val homeworkEntriesStore: KStore<CacheStorage.HomeworkEntriesStorage>
) : CacheLocalDataSource {
    override suspend fun clearCache() {
        profileStore.reset()
        scheduleStore.reset()
        homeworkEntriesStore.reset()
        // TODO: Add other stores
    }

    override suspend fun getProfile(): CacheStorage.ProfileStorage? = profileStore.get()

    override suspend fun saveProfile(
        profile: UserProfile,
        loadedAt: Long
    ) = profileStore.set(CacheStorage.ProfileStorage(profile, loadedAt))

    override suspend fun getSchedule(): CacheStorage.ScheduleStorage? = scheduleStore.get()

    override suspend fun saveSchedule(
        schedule: List<Event>,
        dateRange: LocalDateRange,
        loadedAt: Long
    ) = scheduleStore.set(CacheStorage.ScheduleStorage(schedule, dateRange.run { start to endInclusive }, loadedAt))

    override suspend fun getHomeworkEntries(): CacheStorage.HomeworkEntriesStorage? =
        homeworkEntriesStore.get()

    override suspend fun saveHomeworkEntries(
        homeworkEntries: List<HomeworkEntry>,
        dateRange: LocalDateRange,
        loadedAt: Long
    ) = homeworkEntriesStore.set(
        CacheStorage.HomeworkEntriesStorage(
            homeworkEntries,
            dateRange.run { start to endInclusive },
            loadedAt
        )
    )
}