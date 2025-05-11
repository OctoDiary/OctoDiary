package org.bxkr.octodiary.data.auth.token

import org.bxkr.octodiary.Region

class MesToken(override val value: String) : JsonWebToken() {
    /**
    This property should be accessed only when
    defining token region from plain token string.
     */
    val issuer: Region
        get() {
            val iss = payload?.issuer
            return when (iss) {
                "https://school.mos.ru" -> Region.Moscow
                "https://authedu.mosreg.ru" -> Region.Suburb
                else -> TODO("This region is not implemented! $value")
            }
        }

    val personId: String
        get() = payload?.personId
            ?: throw IllegalArgumentException("MES JWT does not contain person ID. $value")

    val cookie
        get() = "aupd_token=$value"
}