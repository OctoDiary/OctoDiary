package org.bxkr.octodiary.domain.repository

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.group.Group
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.organization.Organization
import org.bxkr.octodiary.domain.model.ranking.Ranking
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.domain.model.visits.VisitDay

interface DiaryRepository {
    val responsibleFor: DiaryId
    suspend fun getProfile(): Result<UserProfile>
    suspend fun getSchedule(dateRange: LocalDateRange): Result<List<Event>>
    suspend fun getHomeworkEntries(
        dateRange: LocalDateRange
    ): Result<List<HomeworkEntry>>

    suspend fun getOrganization(): Result<Organization>
    suspend fun getGroups(): Result<List<Group>>
    suspend fun getRanking(): Result<Ranking>
    suspend fun getEventDescription(eventId: String): Result<Event>
    suspend fun getVisits(dateStart: LocalDate, dateEnd: LocalDate): Result<List<VisitDay>>
}