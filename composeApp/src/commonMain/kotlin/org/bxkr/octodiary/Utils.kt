package org.bxkr.octodiary

import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpMessageBuilder
import io.ktor.utils.io.core.toByteArray
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.toByteString
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.Result.Error
import org.bxkr.octodiary.data.Result.ErrorType
import org.bxkr.octodiary.data.Result.Success
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

val json = Json { ignoreUnknownKeys = true }

inline fun <reified T> String.jwtPayloadTyped() =
    split(".").getOrNull(1)?.let { decodeFromBase64JsonOrNull<T>(it) }

@OptIn(ExperimentalEncodingApi::class)
inline fun <reified T> decodeFromBase64JsonOrNull(
    string: String,
): T? {
    return try {
        json.decodeFromString<T>(
            Base64
                .withPadding(Base64.PaddingOption.PRESENT_OPTIONAL)
                .decode(string)
                .decodeToString()
        )
    } catch (e: RuntimeException) {
        null
    }
}

fun hash(string: String): ByteArray {
    val bytes = string.toByteArray().toByteString()
    return bytes.sha256().toByteArray()
}

@OptIn(ExperimentalEncodingApi::class)
fun encodeToBase64(byteArray: ByteArray): String {
    return Base64.UrlSafe.encode(byteArray).replace("=", "")
}

suspend fun <T> result(
    additionalInfo: String = "not provided",
    block: suspend () -> T
): Result<T> = try {
    Success(block())
} catch (exception: Exception) {
    Error(
        exception.defaultInternalErrorDescription("result block caught this. source: $additionalInfo"),
        ErrorType.Internal
    )
}

suspend fun HttpResponse.defaultErrorDescription() =
    """D'oh! Failed request (body transformation)
        | - Method: ${request.method.value}
        | - Url: ${request.url}
        | - Headers: ${request.headers}
        | - Body: ${request.content}
        | 
        | Response $status
        | - Headers: $headers
        | - Body: ${bodyAsText()}
    """.trimMargin()

fun IOException.defaultNetworkErrorDescription() =
    """D'oh! Network error
        | - Exception: ${this::class.simpleName}
        | - Message: ${message ?: "null"}
        | 
        | Stack trace:
        | ${this.stackTraceToString()}
    """.trimMargin()

fun Exception.defaultInternalErrorDescription(additionalInfo: String? = null) =
    """D'Oh! Internal (code) error
        | - ${if (additionalInfo != null) "Additional Info: $additionalInfo" else "No additional info provided."}
        | - Exception: ${this::class.simpleName}
        | - Message: ${message ?: "null"}
        | 
        | Stack trace:
        | ${this.stackTraceToString()}
    """.trimMargin()

fun HttpMessageBuilder.authHeader(headerValue: String) = header("Authorization", headerValue)