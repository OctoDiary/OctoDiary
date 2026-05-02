package org.bxkr.octodiary.data.datasource.local

import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.domain.model.cache.CacheStorage
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.user.UserProfile

interface CacheLocalDataSource {
    suspend fun clearCache()

    suspend fun getProfile(): CacheStorage.ProfileStorage?
    suspend fun saveProfile(profile: UserProfile, loadedAt: Long)

    suspend fun getSchedule(): CacheStorage.ScheduleStorage?
    suspend fun saveSchedule(
        schedule: List<Event>,
        dateRange: LocalDateRange,
        loadedAt: Long
    )

    suspend fun getHomeworkEntries(): CacheStorage.HomeworkEntriesStorage?
    suspend fun saveHomeworkEntries(
        homeworkEntries: List<HomeworkEntry>,
        dateRange: LocalDateRange,
        loadedAt: Long
    )
}