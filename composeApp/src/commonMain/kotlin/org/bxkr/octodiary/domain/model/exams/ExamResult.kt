package org.bxkr.octodiary.domain.model.exams

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ExamResult(
    val finalResult: String,
    val partResults: List<ExamPartResult>,
    val publishedAt: LocalDateTime
)
