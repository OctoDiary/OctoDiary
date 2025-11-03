package org.bxkr.octodiary.domain.model.diary

data class DiaryCapabilities(
    val hasVisits: Boolean = false,
    val hasMeals: Boolean = false,
    val lessonAttendance: Boolean = false
)
