package org.bxkr.octodiary.data

import kotlinx.coroutines.flow.Flow

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val description: String, val errorType: ErrorType = ErrorType.Internal) :
        Result<Nothing>()

    enum class ErrorType {
        Network,
        Server,
        Internal
    }
}

typealias ResultFlow<T> = Flow<Result<T>>