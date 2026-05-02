package org.bxkr.octodiary.data.repository.diary

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.domain.model.Demo
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.group.Group
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.organization.Organization
import org.bxkr.octodiary.domain.model.ranking.Ranking
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.domain.model.visits.VisitDay
import org.bxkr.octodiary.domain.repository.DiaryRepository
import org.koin.core.annotation.Single

@Single
class DemoRepositoryImpl : DiaryRepository {
    override val responsibleFor = DiaryId.Demo
    private val <T> T.success get() = Result.success(this)

    override suspend fun getProfile(): Result<UserProfile> = Demo.user.success

    override suspend fun getSchedule(dateRange: LocalDateRange): Result<List<Event>> = Demo.schedule.success

    override suspend fun getHomeworkEntries(
        dateRange: LocalDateRange
    ): Result<List<HomeworkEntry>> = Demo.homeworkEntries.success

    override suspend fun getOrganization(): Result<Organization> = Demo.organization.success

    override suspend fun getGroups(): Result<List<Group>> =
        listOf(Demo.mainGroup, Demo.additionalGroup).success

    override suspend fun getRanking(): Result<Ranking> = Demo.ranking.success

    override suspend fun getEventDescription(eventId: String): Result<Event> = Demo.event.success

    override suspend fun getVisits(
        dateStart: LocalDate,
        dateEnd: LocalDate
    ): Result<List<VisitDay>> = Demo.visits.success
}