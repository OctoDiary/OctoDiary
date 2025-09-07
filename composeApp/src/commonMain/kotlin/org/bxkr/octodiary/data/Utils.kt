package org.bxkr.octodiary.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializerOrNull
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import kotlin.reflect.KClass

fun Result<*>.toAuthStepFailure() = try {
    AuthStepResult.Failure(exceptionOrNull()!!, exceptionOrNull()!!.message ?: "No error message")
} catch (nullPointerException: NullPointerException) {
    AuthStepResult.Failure(nullPointerException, "Result is not a failure!")
}

fun IllegalStateException.toAuthStepFailure() = AuthStepResult.Failure(this, "Illegal state exception with message: ${message ?: "NULL"}")

@OptIn(InternalSerializationApi::class)
val KClass<*>.serialName: String? get() = serializerOrNull()?.descriptor?.serialName ?: simpleName