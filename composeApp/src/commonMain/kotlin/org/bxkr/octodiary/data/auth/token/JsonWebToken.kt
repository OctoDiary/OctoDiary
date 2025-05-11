package org.bxkr.octodiary.data.auth.token

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.bxkr.octodiary.jwtPayloadTyped
import org.bxkr.octodiary.model.api.jwt.Payload

abstract class JsonWebToken {
    abstract val value: String

    val payload get() = value.jwtPayloadTyped<Payload>()

    val expirationDate: Instant
        get() {
            val exp = payload?.expiryDate
            if (exp != null) {
                return Instant.fromEpochSeconds(exp)
            } else throw IllegalArgumentException("JWT does not have expiration date in it. $value")
        }

    val bearer get() = "Bearer $value"

    fun isAlive(): Boolean = Clock.System.now() < expirationDate

    override fun toString(): String = value
}