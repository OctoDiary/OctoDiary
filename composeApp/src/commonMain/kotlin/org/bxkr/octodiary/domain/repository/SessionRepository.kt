package org.bxkr.octodiary.domain.repository

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.model.session.Session

interface SessionRepository {
    fun getSessionFlow(): Flow<Session?>

    suspend fun getCurrentSession(): Session?
}