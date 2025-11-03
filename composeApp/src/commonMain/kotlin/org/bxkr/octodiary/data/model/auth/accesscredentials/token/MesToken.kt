package org.bxkr.octodiary.data.model.auth.accesscredentials.token

import io.ktor.client.request.cookie
import io.ktor.http.HttpMessageBuilder
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.region.RegionCode

@Serializable // getter-only props aren't serialized
class MesToken(override val value: String) : JsonWebToken() {
    override val payload: MesPayload?
        get() = value.jwtPayloadTyped<MesPayload>()

    val issuer: RegionCode
        get() {
            val iss = payload?.issuer
            return when (iss) {
                "https://school.mos.ru" -> RegionCode.Moscow
                "https://authedu.mosreg.ru" -> RegionCode.MosReg
                else -> TODO("This region is not implemented! $value")
            }
        }

    val personId: String
        get() = payload?.personId
            ?: throw IllegalArgumentException("MES JWT does not contain person ID. $value")

    val asCookieString
        get() = "aupd_token=$value"

    fun HttpMessageBuilder.aupdCookie() = cookie("aupd_token", value)
}