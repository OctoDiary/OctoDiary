package org.bxkr.octodiary.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.io.IOException
import org.bxkr.octodiary.network.UchebnikApiService
import org.bxkr.octodiary.network.config.UchebnikConfig
import org.bxkr.octodiary.network.exception.FailedConnectionException
import org.bxkr.octodiary.network.getMessage
import org.bxkr.octodiary.network.getWrongResponseTypeException
import org.koin.core.annotation.Single

@Single
class UchebnikApiServiceImpl(
    private val client: HttpClient,
    private val config: UchebnikConfig
) : UchebnikApiService {
    override suspend fun toSchoolToken(
        uchebnikToken: String,
        profileId: String
    ): Result<String> = try {
        val response = client.get(config.baseUrl + "aclx/api/outcoming/aupd") {
            header("Profile-Id", profileId)
            bearerAuth(uchebnikToken)
        }

        val bodyAsText = response.bodyAsText()
        if (!bodyAsText.startsWith("eyJ"))
            Result.failure<String>(getWrongResponseTypeException("to school token in uchebnik api; response = $bodyAsText"))
        Result.success(bodyAsText)
    } catch (exception: IOException) {
        Result.failure(FailedConnectionException(exception.getMessage("to school token in uchebnik api")))
    }
}