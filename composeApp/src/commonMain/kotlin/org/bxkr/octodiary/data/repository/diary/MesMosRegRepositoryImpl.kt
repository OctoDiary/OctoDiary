package org.bxkr.octodiary.data.repository.diary

import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.MesMosRegRemoteDataSource
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.repository.DiaryRepository
import org.bxkr.octodiary.domain.repository.Logger
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single

@Single
class MesMosRegRepositoryImpl(
    private val mesMosRegRemoteDataSource: MesMosRegRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val cacheLocalDataSource: CacheLocalDataSource,
    private val logger: Logger,
) : DiaryRepository, MesRegionalRepositoryImpl(
    mesMosRegRemoteDataSource,
    sessionRepository,
    cacheLocalDataSource,
    logger
) {
    override suspend fun getAccessToken(): MesToken? =
        (sessionRepository.getCurrentSession()?.accessCredentials as? AccessCredentials.MesMosRegAccessCredentials)?.accessToken

    override val responsibleFor = DiaryId.MesMosReg
}