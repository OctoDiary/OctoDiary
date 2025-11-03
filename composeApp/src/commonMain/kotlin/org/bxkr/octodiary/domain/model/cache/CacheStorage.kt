package org.bxkr.octodiary.domain.model.cache

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.user.UserProfile

@Serializable
sealed class CacheStorage {
    abstract val cachedAt: Long

    @Serializable
    data class ProfileStorage(
        val profile: UserProfile,
        override val cachedAt: Long
    ) : CacheStorage()

    @Serializable
    data class ScheduleStorage(
        val schedule: List<Event>,
        val dateRange: Pair<LocalDate, LocalDate>,
        override val cachedAt: Long
    ) : CacheStorage()

    @Serializable
    data class HomeworkEntriesStorage(
        val homeworkEntries: List<HomeworkEntry>,
        val dateRange: Pair<LocalDate, LocalDate>,
        override val cachedAt: Long
    ) : CacheStorage()
}