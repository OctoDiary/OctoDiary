package org.bxkr.octodiary.domain.exception.diary

data class UnknownDiaryException(
    val source: String
) : DiaryException()