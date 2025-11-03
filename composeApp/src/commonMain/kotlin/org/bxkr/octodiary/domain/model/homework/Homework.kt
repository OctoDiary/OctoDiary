package org.bxkr.octodiary.domain.model.homework

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Homework(
    val homeworkId: String,
    val text: String? = null,
    val materials: String? = null,
    val isDone: Boolean? = null,
    val createdAt: LocalDateTime? = null,
    val deadline: LocalDateTime? = null
)
