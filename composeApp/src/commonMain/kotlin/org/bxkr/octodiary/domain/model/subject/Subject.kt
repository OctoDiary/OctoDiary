package org.bxkr.octodiary.domain.model.subject

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.employee.Employee

@Serializable
data class Subject(
    val id: String,
    val name: String,
    val employee: Employee? = null
)