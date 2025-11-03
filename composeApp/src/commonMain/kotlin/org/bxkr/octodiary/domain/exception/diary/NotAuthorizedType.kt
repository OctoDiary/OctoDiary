package org.bxkr.octodiary.domain.exception.diary

sealed class NotAuthorizedType {
    data object SessionNotFound : NotAuthorizedType()
    data object AccessCredentialsNotFound : NotAuthorizedType()
    data object ExpiredAccessCredentials : NotAuthorizedType()
}