package org.bxkr.octodiary

import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpMessageBuilder
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

inline fun <reified T> String.jwtPayloadTyped() =
    split(".").getOrNull(1)?.let { decodeFromBase64JsonOrNull<T>(it) }

@OptIn(ExperimentalEncodingApi::class)
inline fun <reified T> decodeFromBase64JsonOrNull(
    string: String,
): T? {
    return try {
        Json.decodeFromString<T>(
            Base64
                .withPadding(Base64.PaddingOption.PRESENT_OPTIONAL)
                .decode(string)
                .decodeToString()
        )
    } catch (e: RuntimeException) {
        null
    }
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

fun HttpMessageBuilder.authHeader(headerValue: String) = header("Authorization", headerValue)