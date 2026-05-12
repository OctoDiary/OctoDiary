package org.bxkr.octodiary.network

import org.bxkr.octodiary.data.model.api.mes.auth.CodeToTokenResponse

interface AuthEduApiService {
    suspend fun codeToToken(
        code: String,
        state: String
    ): Result<CodeToTokenResponse>
}