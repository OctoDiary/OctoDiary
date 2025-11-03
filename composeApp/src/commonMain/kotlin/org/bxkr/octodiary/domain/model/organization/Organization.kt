package org.bxkr.octodiary.domain.model.organization

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.employee.Employee

@Serializable
data class Organization(
    val fullName: String,
    val type: OrganizationType,
    val contacts: OrganizationContacts? = null,
    val shortName: String? = null,
    val personnel: List<Employee>? = null,
)
