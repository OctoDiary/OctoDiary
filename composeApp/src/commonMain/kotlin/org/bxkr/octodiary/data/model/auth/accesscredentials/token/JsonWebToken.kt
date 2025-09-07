package org.bxkr.octodiary.data.model.auth.accesscredentials.token

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
abstract class JsonWebToken {
    abstract val value: String

    abstract val payload: Payload?

    val expirationDate: Instant
        get() {
            val exp = payload?.expiryDate
            if (exp != null) {
                return Instant.fromEpochSeconds(exp)
            } else throw IllegalArgumentException("JWT does not have expiration date in it. $value")
        }

    val issuedAt: Instant
        get() {
            val iat = payload?.issuedAt
            if (iat != null) {
                return Instant.fromEpochSeconds(iat)
            } else throw IllegalArgumentException("JWT does not have \"iat\" field in it. $value")
        }

    val asBearerHeader get() = "Bearer $value"


    fun isAlive(): Boolean = Clock.System.now() < expirationDate

    override fun toString(): String = value
}