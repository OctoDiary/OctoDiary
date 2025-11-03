package org.bxkr.octodiary.presentation.state

import kotlinx.coroutines.flow.StateFlow
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthMethodData
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.model.diary.Diary
import org.bxkr.octodiary.domain.model.region.Region
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel

data class AuthUiState(
    val currentPage: Int = 0,
    val initialRegionIndex: Int? = null,
    val selectedRegion: Region? = null,
    val isLoading: Boolean = false,
    val availableDiaries: List<Diary>? = null,
    val selectedDiary: Diary? = null,
    val authMethods: List<AuthMethod>? = null,
    val currentAuthStepResult: AuthStepResult? = null,
    val loggedInUser: UserProfile? = null,
    val error: AuthViewModel.ErrorDescription? = null,
    val openLink: AuthMethodData.GoToUrl? = null,
    val additionalPageContent: AuthViewModel.AdditionalPageContent? = null,
    val tokenInfoFlow: StateFlow<TokenInfo>? = null
)