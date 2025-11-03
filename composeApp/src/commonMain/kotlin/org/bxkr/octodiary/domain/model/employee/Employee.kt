package org.bxkr.octodiary.domain.model.employee

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val fullName: String,
    val type: EmployeeType,
    val contacts: EmployeeContacts? = null,
    val affiliation: String? = null,
)
