package org.bxkr.octodiary.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpMessageBuilder
import kotlinx.io.IOException
import org.bxkr.octodiary.data.model.api.mes.profile.ProfileResponse
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.network.SchoolMesApiService
import org.bxkr.octodiary.network.config.SchoolMesApiConfig
import org.bxkr.octodiary.network.exception.FailedConnectionException
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
class SchoolMesApiServiceImpl(
    private val client: HttpClient,
    @InjectedParam private val config: SchoolMesApiConfig
) : SchoolMesApiService {
    private companion object {
        const val X_MES_SUBSYSTEM_HEADER = "x-mes-subsystem"
        const val FAMILY_MP = "familymp"
        const val AUTH_TOKEN_HEADER = "auth-token"
    }

    override suspend fun getProfile(accessToken: MesToken): Result<ProfileResponse> = try {
        val response = client.get(config.baseUrl + "api/family/mobile/v1/profile") {
            mesAuth(accessToken)
            xMesSubsystemHeader()
        }
        Result.success(response.body())
    } catch (_: IOException) {
        Result.failure(FailedConnectionException("Failed to execute getProfile in school mes api"))
    } catch (exception: NoTransformationFoundException) {
        Result.failure(exception)
    }

    private fun HttpMessageBuilder.xMesSubsystemHeader(value: String = FAMILY_MP) = header(
        X_MES_SUBSYSTEM_HEADER, value
    )

    private fun HttpMessageBuilder.mesAuth(mesToken: MesToken) {
        header(AUTH_TOKEN_HEADER, mesToken.value)
        bearerAuth(mesToken.value)
        with(mesToken) { aupdCookie() }
    }
}