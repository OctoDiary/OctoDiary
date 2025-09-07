package org.bxkr.octodiary.data.datasource.remote

import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallResponse
import org.bxkr.octodiary.data.model.api.mes.auth.MosToMes
import org.bxkr.octodiary.data.model.api.mes.auth.TokenExchange
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken

interface MesMosRemoteDataSource : MesLikeRemoteDataSource {
    suspend fun mosRuIssueCall(): Result<IssueCallResponse>

    suspend fun handleCode(
        code: String,
        credentials: MosRuInfo
    ): Result<TokenExchange>

    suspend fun mosToMes(
        mosToken: String
    ): Result<MesToken>
}