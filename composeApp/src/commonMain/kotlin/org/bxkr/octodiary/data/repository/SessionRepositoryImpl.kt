package org.bxkr.octodiary.data.repository

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.di.annotation.MainStorage
import org.bxkr.octodiary.domain.model.session.Session
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.koin.core.annotation.Single

@Single
class SessionRepositoryImpl(
    @param:MainStorage private val kStore: KStore<StorageLatest>
) : SessionRepository {
    private fun mapStorageToSession(storage: StorageLatest?) = storage?.accessCredentials?.let {
        Session(
            accessCredentials = it
        )
    }

    override fun getSessionFlow(): Flow<Session?> = kStore.updates.map(::mapStorageToSession)

    override suspend fun getCurrentSession(): Session? = kStore.get().let(::mapStorageToSession)
}