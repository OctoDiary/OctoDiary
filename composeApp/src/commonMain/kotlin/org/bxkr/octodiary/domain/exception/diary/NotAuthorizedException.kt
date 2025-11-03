package org.bxkr.octodiary.domain.exception.diary

class NotAuthorizedException(
    val notAuthorizedType: NotAuthorizedType
) : DiaryException()