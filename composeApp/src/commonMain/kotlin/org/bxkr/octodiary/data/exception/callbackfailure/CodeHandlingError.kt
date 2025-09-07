package org.bxkr.octodiary.data.exception.callbackfailure

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.error.DomainError


@Serializable
class CodeHandlingError(
    override val exceptionMessage: String? = null,
    override val exceptionStackTrace: String? = null
) : DomainError(
    type = CallbackHandlingFailureException.TYPE,
    cause = "code-handling-error",
)