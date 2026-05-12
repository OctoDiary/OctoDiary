package org.bxkr.octodiary.data.repository.diary

import kotlinx.datetime.LocalDate
import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.MesMosRegRemoteDataSource
import org.bxkr.octodiary.domain.exception.diary.UnsupportedFeatureException
import org.bxkr.octodiary.domain.model.visits.VisitDay
import org.bxkr.octodiary.domain.repository.Logger
import org.bxkr.octodiary.domain.repository.SessionRepository

abstract class MesRegionalRepositoryImpl(
    private val mesMosRegRemoteDataSource: MesMosRegRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val cacheLocalDataSource: CacheLocalDataSource,
    private val logger: Logger,
) : MesLikeRepositoryImpl(mesMosRegRemoteDataSource, sessionRepository, cacheLocalDataSource, logger) {
    final override suspend fun getVisits(
        dateStart: LocalDate, dateEnd: LocalDate
    ): Result<List<VisitDay>> = Result.failure(UnsupportedFeatureException())
}