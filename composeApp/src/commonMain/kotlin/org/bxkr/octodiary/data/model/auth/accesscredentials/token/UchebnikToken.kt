package org.bxkr.octodiary.data.model.auth.accesscredentials.token

import io.ktor.client.request.cookie
import io.ktor.http.HttpMessageBuilder
import kotlinx.serialization.Serializable

@Serializable // getter-only props aren't serialized
class UchebnikToken(override val value: String) : JsonWebToken() {
    override val payload: UchebnikPayload?
        get() = value.jwtPayloadTyped<UchebnikPayload>()

    val profileId: String
        get() = payload?.profiles?.firstOrNull()?.id
            ?: throw IllegalArgumentException("Uchebnik JWT does not contain profile ID. $value")

    val asCookieString
        get() = "aupd_token=$value"

    fun HttpMessageBuilder.aupdCookie() = cookie("aupd_token", value)
}