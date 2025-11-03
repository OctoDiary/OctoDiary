package org.bxkr.octodiary.domain.model.employee

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeContacts(
    val phoneNumber: String? = null,
    val email: String? = null,
    val personalClassroom: String? = null,
    val physicalAddress: String? = null
)
