package org.bxkr.octodiary.domain.model.exams

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Exam(
    val type: String,
    val subjectName: String,
    val date: LocalDate,
    val isApprobation: Boolean
)
