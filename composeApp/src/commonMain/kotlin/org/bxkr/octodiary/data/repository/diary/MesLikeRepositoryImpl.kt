package org.bxkr.octodiary.data.repository.diary

import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.MesLikeRemoteDataSource
import org.bxkr.octodiary.data.mapper.mes.toDomain
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedException
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedType
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.group.Group
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.log.LogLevel
import org.bxkr.octodiary.domain.model.log.LogTemplate
import org.bxkr.octodiary.domain.model.organization.Organization
import org.bxkr.octodiary.domain.model.ranking.Ranking
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.domain.repository.DiaryRepository
import org.bxkr.octodiary.domain.repository.Logger
import org.bxkr.octodiary.domain.repository.SessionRepository
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
abstract class MesLikeRepositoryImpl(
    private val mesLikeRemoteDataSource: MesLikeRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val cacheLocalDataSource: CacheLocalDataSource,
    private val logger: Logger
) : DiaryRepository {
    private val cacheLifetime = 12.hours

    @OptIn(ExperimentalTime::class)
    protected fun isCacheExpired(cachedAtMillis: Long): Boolean {
        val cacheLifetimeMillis = cacheLifetime.inWholeMilliseconds
        return (Clock.System.now().toEpochMilliseconds() - cachedAtMillis) > cacheLifetimeMillis
    }

    abstract suspend fun getAccessToken(): MesToken?

    final override suspend fun getProfile(): Result<UserProfile> {
        val cacheEntry = cacheLocalDataSource.getProfile()
        if (cacheEntry != null && !isCacheExpired(cacheEntry.cachedAt)) {
            return Result.success(cacheEntry.profile)
        }
        val accessToken = getAccessToken() ?: return Result.failure(
            NotAuthorizedException(
                NotAuthorizedType.AccessCredentialsNotFound
            )
        )

        if (!accessToken.isAlive()) return Result.failure(
            NotAuthorizedException(
                NotAuthorizedType.ExpiredAccessCredentials
            )
        )

        val remoteResult = mesLikeRemoteDataSource.getProfile(accessToken)

        return remoteResult.fold(onSuccess = {
            logger.log(
                LogTemplate.successfullyLoaded(
                    loadedPartName = "profile", source = "$responsibleFor repository"
                )
            )
            val domainProfile = it.toDomain()
            cacheLocalDataSource.saveProfile(
                domainProfile, Clock.System.now().toEpochMilliseconds()
            )
            Result.success(domainProfile)
        }, onFailure = {
            logger.log(
                ("Couldn't load profile" +
                        "| - $responsibleFor repository" +
                        "| - exception name: ${it::class.simpleName}" +
                        "| - message: ${it.message}" +
                        "| - stack trace: ${it.stackTraceToString()}").trimMargin(),
                LogLevel.ERROR
            )
            Result.failure(it)
        })
    }

    final override suspend fun getSchedule(dateRange: LocalDateRange): Result<List<Event>> {
        TODO("Not yet implemented")
    }

    final override suspend fun getHomeworkEntries(
        dateRange: LocalDateRange
    ): Result<List<HomeworkEntry>> {
        TODO("Not yet implemented")
    }

    final override suspend fun getOrganization(): Result<Organization> {
        TODO("Not yet implemented")
    }

    final override suspend fun getGroups(): Result<List<Group>> {
        TODO("Not yet implemented")
    }

    final override suspend fun getRanking(): Result<Ranking> {
        TODO("Not yet implemented")
    }

    final override suspend fun getEventDescription(eventId: String): Result<Event> {
        TODO("Not yet implemented")
    }
}