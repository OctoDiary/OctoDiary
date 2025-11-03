package org.bxkr.octodiary.domain.model.organization

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationContacts(
    val phoneNumber: String? = null,
    val email: String? = null,
    val website: String? = null,
    val physicalAddress: String? = null
)
