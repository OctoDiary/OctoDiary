package org.bxkr.octodiary.data.model.auth.accesscredentials.token

import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

val jsonTokenUtils = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> String.jwtPayloadTyped() =
    split(".").getOrNull(1)?.let { decodeFromBase64JsonOrNull<T>(it) }

@OptIn(ExperimentalEncodingApi::class)
inline fun <reified T> decodeFromBase64JsonOrNull(
    string: String,
): T? {
    return try {
        jsonTokenUtils.decodeFromString<T>(
            Base64
                .withPadding(Base64.PaddingOption.PRESENT_OPTIONAL)
                .decode(string)
                .decodeToString()
        )
    } catch (_: RuntimeException) {
        null
    }
}