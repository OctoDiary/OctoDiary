package org.bxkr.octodiary.di.module

import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path
import org.bxkr.octodiary.di.annotation.HomeworkEntriesCache
import org.bxkr.octodiary.di.annotation.ProfileCache
import org.bxkr.octodiary.di.annotation.ScheduleCache
import org.bxkr.octodiary.domain.model.cache.CacheId
import org.bxkr.octodiary.domain.model.cache.CacheStorage
import org.bxkr.octodiary.getPaths
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Module
class CacheModule {
    private fun getPath(id: CacheId, scope: Scope) = Path("${getPaths(scope).files}/cache_${id.storeKey}")

    @Single
    @ProfileCache
    fun profileStorage(scope: Scope) = storeOf<CacheStorage.ProfileStorage>(
        getPath(CacheId.Profile, scope)
    )

    @Single
    @ScheduleCache
    fun scheduleStorage(scope: Scope) = storeOf<CacheStorage.ScheduleStorage>(
        getPath(CacheId.Schedule, scope)
    )

    @Single
    @HomeworkEntriesCache
    fun homeworkEntriesStorage(scope: Scope) = storeOf<CacheStorage.HomeworkEntriesStorage>(
        getPath(CacheId.HomeworkEntries, scope)
    )
}