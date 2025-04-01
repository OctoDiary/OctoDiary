package org.bxkr.octodiary.data.token

import org.bxkr.octodiary.network.apis.Region

class MesToken(override val value: String) : JsonWebToken() {
    val issuer: Region
        get() {
            val iss = getJwtFieldValue<String>("iss")
            return when (iss) {
                "https://school.mos.ru" -> Region.Moscow
                "https://authedu.mosreg.ru" -> Region.Suburb
                else -> TODO("This region is not implemented! $value")
            }
        }

    val personId: String
        get() = getJwtFieldValue<String>("msh")
            ?: throw IllegalArgumentException("MES JWT does not contain person ID. $value")

    val cookie
        get() = "aupd_token=$value"
}