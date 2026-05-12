package org.bxkr.octodiary.data.datasource.remote.impl

import org.bxkr.octodiary.data.datasource.remote.MesMosRegRemoteDataSource
import org.bxkr.octodiary.data.model.api.mes.auth.RegionalTokens
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.network.AuthEduApiService
import org.bxkr.octodiary.network.UchebnikApiService
import org.bxkr.octodiary.network.config.SchoolMesApiConfig
import org.koin.core.annotation.Single

@Single
class MesMosRegRemoteDataSourceImpl(
    uchebnikApiService: UchebnikApiService,
    val authEduApiService: AuthEduApiService
) : MesLikeRemoteDataSourceImpl(
    DiaryId.MesMosReg,
    SchoolMesApiConfig("https://authedu.mosreg.ru/"),
    uchebnikApiService
), MesMosRegRemoteDataSource {
    override suspend fun codeToToken(
        code: String,
        state: String
    ): Result<RegionalTokens> = authEduApiService.codeToToken(code, state)
        .map { RegionalTokens(accessToken = MesToken(it.token), refreshToken = it.refreshToken) }
}