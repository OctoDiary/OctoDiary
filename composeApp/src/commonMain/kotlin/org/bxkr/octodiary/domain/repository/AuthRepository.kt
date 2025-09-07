package org.bxkr.octodiary.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.bxkr.octodiary.domain.model.Diary
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.domain.model.Region
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.model.auth.LogoutResult
import org.bxkr.octodiary.domain.model.auth.TokenInfo

interface AuthRepository {
    fun getAuthStateFlow(): Flow<AuthState>
    suspend fun processAuthStep(credentials: Credentials, diarySystem: Diary): AuthStepResult
    suspend fun logout(): LogoutResult
    suspend fun getRegionDiaries(region: Region): List<Diary>
    suspend fun getAuthMethods(diary: Diary): List<AuthMethod>
    suspend fun startCollectingDeeplink()
    suspend fun finishCallbackHandling()
    fun checkToken(token: String, diaryId: DiaryId): Flow<TokenInfo>
}