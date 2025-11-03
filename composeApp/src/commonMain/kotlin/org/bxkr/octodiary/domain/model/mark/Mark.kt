package org.bxkr.octodiary.domain.model.mark

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.employee.Employee
import org.bxkr.octodiary.domain.model.subject.Subject

@Serializable
data class Mark(
    val subject: Subject,
    val components: List<MarkComponent>,
    val workType: String? = null,
    val groupResults: List<MarkGroupResult>? = null,
    val setBy: Employee? = null,
    val setAt: LocalDateTime? = null
)
