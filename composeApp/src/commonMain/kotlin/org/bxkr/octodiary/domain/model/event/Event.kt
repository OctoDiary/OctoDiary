package org.bxkr.octodiary.domain.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.employee.Employee
import org.bxkr.octodiary.domain.model.homework.Homework
import org.bxkr.octodiary.domain.model.mark.Mark
import org.bxkr.octodiary.domain.model.subject.Subject
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Serializable
data class Event(
    val eventId: String,
    val eventName: String,
    val type: EventType,

    val timeStart: LocalDateTime? = null,
    val timeEnd: LocalDateTime? = null,
    val isAllDay: Boolean? = null,
    val subject: Subject? = null,
    val employee: Employee? = null,
    val location: EventLocation? = null,
    val homework: Homework? = null,
    val marks: List<Mark> = emptyList(),
    val isAttended: Boolean? = null,
    val additionalInformation: EventAdditionalInformation? = null
)
