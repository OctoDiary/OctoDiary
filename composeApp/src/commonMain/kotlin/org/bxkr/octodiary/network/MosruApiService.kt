package org.bxkr.octodiary.network

import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallResponse
import org.bxkr.octodiary.data.model.api.mes.auth.MosToMes
import org.bxkr.octodiary.data.model.api.mes.auth.TokenExchange
import org.bxkr.octodiary.data.model.auth.MosRuInfo

interface MosruApiService {
    suspend fun issueCall(): Result<IssueCallResponse>
    suspend fun handleCode(
        code: String,
        credentials: MosRuInfo
    ): Result<TokenExchange>
    suspend fun mosToMes(mosToken: String): Result<MosToMes.MosToMesResponse>
    suspend fun refreshToken(): Result<TokenExchange.Refresh>
}