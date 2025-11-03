package org.bxkr.octodiary.domain.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EventAdditionalInformation(
    val description: String? = null,
    val conferenceLink: String? = null
)
