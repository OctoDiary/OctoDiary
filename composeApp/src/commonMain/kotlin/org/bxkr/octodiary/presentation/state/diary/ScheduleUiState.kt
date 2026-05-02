package org.bxkr.octodiary.presentation.state.diary

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import org.bxkr.octodiary.domain.exception.diary.DiaryException
import org.bxkr.octodiary.domain.model.event.Event

data class ScheduleUiState(
    val isLoading: Boolean = false,
    val error: DiaryException? = null,
    val currentDate: LocalDate? = null,
    val loadedEvents: List<Event>? = null,
    val loadedEventsDateRange: LocalDateRange? = null
) {
    val needToLoad get() = loadedEvents == null && !isLoading && error == null
}
