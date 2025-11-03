package org.bxkr.octodiary.domain.model.exams

import kotlinx.serialization.Serializable

@Serializable
data class ExamPartResult(
    val partName: String?,
    val result: Result
)
