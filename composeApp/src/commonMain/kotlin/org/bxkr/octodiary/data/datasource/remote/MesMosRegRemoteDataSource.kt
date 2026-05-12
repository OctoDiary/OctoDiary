package org.bxkr.octodiary.data.datasource.remote

import org.bxkr.octodiary.data.model.api.mes.auth.RegionalTokens

interface MesMosRegRemoteDataSource : MesLikeRemoteDataSource {
    suspend fun codeToToken(
        code: String,
        state: String
    ): Result<RegionalTokens>
}