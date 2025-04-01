package org.bxkr.octodiary.data.token

import org.bxkr.octodiary.jwtPayloadTyped
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

abstract class JsonWebToken {
    abstract val value: String

    @OptIn(ExperimentalTime::class)
    val expirationDate: Instant
        get() {
            val exp = getJwtFieldValue<Number>("exp")?.toLong()
            if (exp != null) {
                return Instant.fromEpochSeconds(exp)
            } else throw IllegalArgumentException("JWT does not have expiration date in it. $value")
        }

    val bearer get() = "Bearer $value"

    @OptIn(ExperimentalTime::class)
    fun isAlive(): Boolean = Clock.System.now() < expirationDate

    protected inline fun <reified T> getJwtFieldValue(key: String): T? {
        val payload = value.jwtPayloadTyped<Map<String, Any>>()
        val element = payload?.get(key) ?: return null
        return if (element is T) {
            element
        } else null
    }

    override fun toString(): String = value
}