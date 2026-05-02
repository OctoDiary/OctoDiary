package org.bxkr.octodiary.domain.model.cache

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
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
        val dateRangePair: Pair<LocalDate, LocalDate>,
        override val cachedAt: Long
    ) : CacheStorage() {
        val dateRange get() = dateRangePair.run { LocalDateRange(first, second) }
    }

    @Serializable
    data class HomeworkEntriesStorage(
        val homeworkEntries: List<HomeworkEntry>,
        val dateRangePair: Pair<LocalDate, LocalDate>,
        override val cachedAt: Long
    ) : CacheStorage() {
        val dateRange get() = dateRangePair.run { LocalDateRange(first, second) }
    }
}