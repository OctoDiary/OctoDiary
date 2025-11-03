package org.bxkr.octodiary.domain.model.visits

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class VisitDay(
    val date: LocalDate,
    val visits: List<Visit>
)
