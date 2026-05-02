package org.bxkr.octodiary.data.repository.diary

import org.bxkr.octodiary.data.datasource.local.CacheLocalDataSource
import org.bxkr.octodiary.data.datasource.remote.impl.MesMosRegRemoteDataSource
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
    override val responsibleFor = DiaryId.MesMosReg
}