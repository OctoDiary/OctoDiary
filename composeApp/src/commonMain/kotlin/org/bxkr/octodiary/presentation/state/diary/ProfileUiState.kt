package org.bxkr.octodiary.presentation.state.diary

import org.bxkr.octodiary.domain.exception.diary.DiaryException
import org.bxkr.octodiary.domain.model.user.UserProfile

data class ProfileUiState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = false,
    val error: DiaryException? = null
) {
    val needToLoad get() = profile == null && !isLoading && error == null
}