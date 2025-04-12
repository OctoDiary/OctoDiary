package org.bxkr.octodiary.data

import kotlinx.coroutines.flow.Flow

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

typealias ResultFlow<T> = Flow<Result<T>>