package org.bxkr.octodiary.network.impl

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.io.IOException
import org.bxkr.octodiary.data.model.api.mes.auth.CodeToTokenResponse
import org.bxkr.octodiary.network.AuthEduApiService
import org.bxkr.octodiary.network.config.AuthEduConfig
import org.bxkr.octodiary.network.exception.FailedConnectionException
import org.bxkr.octodiary.network.getMessage
import org.koin.core.annotation.Single

@Single
class AuthEduApiServiceImpl(
    private val client: HttpClient,
    private val config: AuthEduConfig
) : AuthEduApiService {
    override suspend fun codeToToken(
        code: String,
        state: String
    ): Result<CodeToTokenResponse> = try {
        val response = client.get(config.baseUrl + "v3/auth/token") {
            parameter("code", code)
            parameter("state", state)
        }

        Result.success(response.body())
    } catch (exception: IOException) {
        Result.failure(FailedConnectionException(exception.getMessage("code to token in authedu")))
    }
}