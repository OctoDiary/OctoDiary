package org.bxkr.octodiary.data.exception.callbackfailure

import org.bxkr.octodiary.domain.model.error.DomainError

class CallbackHandlingFailureException(
    val domainError: DomainError
) : Exception() {
    companion object {
        const val TYPE = "callback-handling-failure"
    }
}