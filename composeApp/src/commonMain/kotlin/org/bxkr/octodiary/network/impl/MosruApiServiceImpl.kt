package org.bxkr.octodiary.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.utils.io.core.toByteArray
import kotlinx.io.IOException
import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallBody
import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallResponse
import org.bxkr.octodiary.data.model.api.mes.auth.MosToMes
import org.bxkr.octodiary.data.model.api.mes.auth.TokenExchange
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.network.MosruApiService
import org.bxkr.octodiary.network.authHeader
import org.bxkr.octodiary.network.config.MosruApiConfig
import org.bxkr.octodiary.network.exception.FailedConnectionException
import org.bxkr.octodiary.network.getMessage
import org.koin.core.annotation.Single
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Single
class MosruApiServiceImpl(
    private val client: HttpClient,
    private val config: MosruApiConfig
) : MosruApiService {
    private companion object Constants {
        const val AUTH_ISSUER_SECRET =
            "Bearer FqzGn1dTJ9BQCHgV0rmMjtYFIgaFf9TrGVEzgtju-zbtIbeJSkIyDcl0e2QMirTNpEqovTT8NvOLZI0XklVEIw"
        const val MOCK_SOFTWARE_STATEMENT =
            "eyJ0eXAiOiJKV1QiLCJibGl0ejpraW5kIjoiU09GVF9TVE0iLCJhbGciOiJSUzI1NiJ9.eyJncmFudF90eXBlcyI6WyJhdXRob3JpemF0aW9uX2NvZGUiLCJwYXNzd29yZCIsImNsaWVudF9jcmVkZW50aWFscyIsInJlZnJlc2hfdG9rZW4iXSwic2NvcGUiOiJiaXJ0aGRheSBibGl0el9jaGFuZ2VfcGFzc3dvcmQgYmxpdHpfYXBpX3VzZWNfY2hnIGJsaXR6X3VzZXJfcmlnaHRzIGNvbnRhY3RzIG9wZW5pZCBwcm9maWxlIGJsaXR6X3JtX3JpZ2h0cyBibGl0el9hcGlfc3lzX3VzZXJfY2hnIGJsaXR6X2FwaV9zeXNfdXNlcnMgYmxpdHpfYXBpX3N5c191c2Vyc19jaGcgc25pbHMgYmxpdHpfYXBpX3N5c191c2VjX2NoZyBibGl0el9xcl9hdXRoIiwianRpIjoiYTVlM2NiMGQtYTBmYi00ZjI1LTk3ODctZTllYzRjOTFjM2ZkIiwic29mdHdhcmVfaWQiOiJkbmV2bmlrLm1vcy5ydSIsInNvZnR3YXJlX3ZlcnNpb24iOiIxIiwicmVzcG9uc2VfdHlwZXMiOlsiY29kZSIsInRva2VuIl0sImlhdCI6MTYzNjcyMzQzOSwiaXNzIjoiaHR0cHM6Ly9sb2dpbi5tb3MucnUiLCJyZWRpcmVjdF91cmlzIjpbImh0dHA6Ly9sb2NhbGhvc3QiLCJzaGVsbDovL2F1dGhwb3J0YWwiLCJkbmV2bmlrLW1lczovL29hdXRoMnJlZGlyZWN0IiwiaHR0cHM6Ly9zY2hvb2wubW9zLnJ1L2F1dGgvbWFpbi9jYWxsYmFjayIsImh0dHBzOi8vc2Nob29sLm1vcy5ydS92MS9vYXV0aC9jYWxsYmFjayIsImh0dHBzOi8vZG5ldm5pay5tb3MucnUvc3VkaXIiLCJodHRwczovL3NjaG9vbC5tb3MucnUvYXV0aC9jYWxsYmFjayIsImh0dHA6Ly9kbmV2bmlrLm1vcy5ydS9zdWRpciJdLCJhdWQiOlsiZG5ldm5pay5tb3MucnUiXX0.EERWGw5RGhLQ1vBiGrdG_eJrCyJEyan-H4UWT1gr4B9ZfP58pyJoVw5wTt8YFqzwbvHNQBnvrYfMCzOkHpsU7TxlETJpbWcWbnV5JI-inzXGyKCic2fAVauVCjos3v6AFiP6Uw6ZXIC6b9kQ5WgRVM66B9UwAB2MKTThTohJP7_MNZJ0RiOd8RLlvF4C7yfuqoGU2-KWLwr78ATniTvYFWszl8jAi_SiD9Ai1GWW4mO9-JQ01f4N9umC5Cy2tYiZhxbaz2rOsAQBBjY6rbCCJbCpb1lyGfs2qhhAB-ODGTq7W7r1WBlAm5EXlPpuW_9pi8uxdxiqjkG3d6xy7h7gtQ"
        const val SOFTWARE_ID = "dnevnik.mos.ru"
        const val DEVICE_TYPE = "android_phone"
        const val GRANT_TYPE_CODE = "authorization_code"
        const val REDIRECT_URI = "dnevnik-mes://oauth2redirect"
        const val GRANT_TYPE_REFRESH = "refresh_token"
    }

    override suspend fun issueCall(): Result<IssueCallResponse> = try {
        val response = client.post(config.baseUrl + "sps/oauth/register") {
            authHeader(AUTH_ISSUER_SECRET)
            contentType(ContentType.Application.Json)
            setBody(
                IssueCallBody(
                    SOFTWARE_ID,
                    DEVICE_TYPE,
                    MOCK_SOFTWARE_STATEMENT
                )
            )
        }

        try {
            Result.success(response.body())
        } catch (exception: NoTransformationFoundException) {
            Result.failure(exception)
        }
    } catch (exception: IOException) {
        Result.failure(FailedConnectionException(exception.getMessage("issueCall in mos.ru API")))
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeToBase64(byteArray: ByteArray): String {
        return Base64.UrlSafe.encode(byteArray).replace("=", "")
    }

    override suspend fun handleCode(code: String, credentials: MosRuInfo): Result<TokenExchange> = try {
        val authorization =
            encodeToBase64("${credentials.clientId}:${credentials.clientSecret}".toByteArray())
        val authHeader = "Basic $authorization"

        val response = client.submitForm(
            config.baseUrl + "sps/oauth/te",
            formParameters = parameters {
                append("grant_type",
                    GRANT_TYPE_CODE
                )
                append("redirect_uri",
                   REDIRECT_URI
                )
                append("code", code)
                append("code_verifier", credentials.codeVerifier)
            }
        ) {
            authHeader(authHeader)
        }

        try {
            Result.success(response.body())
        } catch (exception: NoTransformationFoundException) {
            Result.failure(exception)
        }
    } catch (exception: IOException) {
        Result.failure(FailedConnectionException(exception.getMessage("handleCode in mos.ru API")))
    }

    override suspend fun mosToMes(mosToken: String): Result<MosToMes.MosToMesResponse> = try {
        val response = client.post(config.mosToMesUrl) {
            contentType(ContentType.Application.Json)
            setBody(MosToMes(MosToMes.MosToMesRequest(mosToken)))
        }

        try {
            Result.success(response.body<MosToMes.MosToMesResponse>())
        } catch (exception: NoTransformationFoundException) {
            Result.failure(exception)
        }
    } catch (exception: IOException) {
        Result.failure(FailedConnectionException(exception.getMessage("mosToMes in mos.ru (mes) API")))
    }

    override suspend fun refreshToken(): Result<TokenExchange.Refresh> {
        TODO("Not yet implemented")
    }
}