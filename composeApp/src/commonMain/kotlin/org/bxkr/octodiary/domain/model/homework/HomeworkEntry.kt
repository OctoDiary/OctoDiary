package org.bxkr.octodiary.domain.model.homework

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.subject.Subject

@Serializable
data class HomeworkEntry(
    val subject: Subject,
    val deadline: LocalDate,
    val homeworks: List<Homework>,
)
