package org.bxkr.octodiary.data

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}