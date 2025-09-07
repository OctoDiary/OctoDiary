package org.bxkr.octodiary.domain.model.error

import kotlinx.serialization.Serializable

@Serializable
abstract class DomainError (
    val type: String,
    val cause: String,
) {
    abstract val exceptionMessage: String?
    abstract val exceptionStackTrace: String?
}