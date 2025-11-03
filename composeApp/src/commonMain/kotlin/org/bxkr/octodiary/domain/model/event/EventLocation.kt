package org.bxkr.octodiary.domain.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EventLocation(
    val name: String,
    val branch: String? = null
)
