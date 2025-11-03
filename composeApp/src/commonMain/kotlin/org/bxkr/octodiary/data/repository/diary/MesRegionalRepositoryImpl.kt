package org.bxkr.octodiary.data.repository.diary

import kotlinx.datetime.LocalDate
import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.impl.MesMosRegRemoteDataSource
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.exception.diary.UnsupportedFeatureException
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.visits.VisitDay
import org.bxkr.octodiary.domain.repository.SessionRepository

abstract class MesRegionalRepositoryImpl(
    private val mesMosRegRemoteDataSource: MesMosRegRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val cacheLocalDataSource: CacheLocalDataSource
) : MesLikeRepositoryImpl(mesMosRegRemoteDataSource, sessionRepository, cacheLocalDataSource) {

    final override suspend fun getAccessToken(): MesToken? =
        (sessionRepository.getCurrentSession()?.accessCredentials as? AccessCredentials.MesRegionalAccessCredentials)?.accessToken

    final override suspend fun getVisits(
        dateStart: LocalDate, dateEnd: LocalDate
    ): Result<List<VisitDay>> = Result.failure(UnsupportedFeatureException())
}