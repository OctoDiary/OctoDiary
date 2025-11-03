package org.bxkr.octodiary.data.repository.diary

import kotlinx.datetime.LocalDate
import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.MesMosRemoteDataSource
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.visits.VisitDay
import org.bxkr.octodiary.domain.repository.DiaryRepository
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single

@Single
class MesMosRepositoryImpl(
    private val mesMosRemoteDataSource: MesMosRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val cacheLocalDataSource: CacheLocalDataSource
) : DiaryRepository, MesLikeRepositoryImpl(
    mesMosRemoteDataSource, sessionRepository, cacheLocalDataSource
) {
    override val responsibleFor = DiaryId.MesMos

    override suspend fun getAccessToken(): MesToken? =
        (sessionRepository.getCurrentSession()?.accessCredentials as? AccessCredentials.MesMosAccessCredentials)?.accessToken

    override suspend fun getVisits(
        dateStart: LocalDate, dateEnd: LocalDate
    ): Result<List<VisitDay>> {
        TODO("Not yet implemented")
    }
}