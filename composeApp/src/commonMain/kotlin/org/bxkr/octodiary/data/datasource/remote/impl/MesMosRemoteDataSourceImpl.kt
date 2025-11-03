package org.bxkr.octodiary.data.datasource.remote.impl

import org.bxkr.octodiary.data.datasource.remote.MesMosRemoteDataSource
import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallResponse
import org.bxkr.octodiary.data.model.api.mes.auth.TokenExchange
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.network.MosruApiService
import org.bxkr.octodiary.network.UchebnikApiService
import org.bxkr.octodiary.network.config.SchoolMesApiConfig
import org.koin.core.annotation.Single

@Single
class MesMosRemoteDataSourceImpl(
    private val mosruApiService: MosruApiService,
    uchebnikApiService: UchebnikApiService
) : MesLikeRemoteDataSourceImpl(
    DiaryId.MesMos,
    SchoolMesApiConfig("https://school.mos.ru/"),
    uchebnikApiService
), MesMosRemoteDataSource {
    override suspend fun mosRuIssueCall(): Result<IssueCallResponse> =
        mosruApiService.issueCall()

    override suspend fun handleCode(
        code: String,
        credentials: MosRuInfo
    ): Result<TokenExchange> =
        mosruApiService.handleCode(code, credentials)

    override suspend fun mosToMes(mosToken: String): Result<MesToken> =
        mosruApiService.mosToMes(mosToken).map { MesToken(it.response.meshAccessToken) }
}