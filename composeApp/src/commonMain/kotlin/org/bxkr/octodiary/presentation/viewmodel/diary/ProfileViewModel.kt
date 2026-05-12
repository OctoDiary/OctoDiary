package org.bxkr.octodiary.presentation.viewmodel.diary

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.domain.exception.diary.DiaryException
import org.bxkr.octodiary.domain.exception.diary.UnknownDiaryException
import org.bxkr.octodiary.domain.model.log.LogTemplate
import org.bxkr.octodiary.domain.repository.Logger
import org.bxkr.octodiary.domain.usecase.diary.GetProfileUseCase
import org.bxkr.octodiary.presentation.state.diary.ProfileUiState
import org.bxkr.octodiary.presentation.viewmodel.BaseViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val logger: Logger
) : BaseViewModel<ProfileUiState>() {
    override val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())

    fun loadProfile() {
        uu { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getProfileUseCase()
            result.fold(
                onSuccess = { profile ->
                    uu { it.copy(profile = profile) }
                },
                onFailure = { exception ->
                    val diaryException = (exception as? DiaryException)
                        ?: UnknownDiaryException(source = "loadProfile() in ProfileViewModel")
                    if (diaryException is UnknownDiaryException) {
                        logger.log(LogTemplate.unknownDiaryException(diaryException))
                    }
                    uu { it.copy(error = diaryException) }
                }
            )
            uu { it.copy(isLoading = false) }
        }
    }
}