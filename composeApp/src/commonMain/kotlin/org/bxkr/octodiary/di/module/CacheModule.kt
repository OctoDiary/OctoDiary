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

@Module
class CacheModule {
    private fun getPath(id: CacheId) = Path("${getPaths().files}/cache_${id.storeKey}")

    @Single
    @ProfileCache
    fun profileStorage() = storeOf<CacheStorage.ProfileStorage>(
        getPath(CacheId.Profile)
    )

    @Single
    @ScheduleCache
    fun scheduleStorage() = storeOf<CacheStorage.ScheduleStorage>(
        getPath(CacheId.Schedule)
    )

    @Single
    @HomeworkEntriesCache
    fun homeworkEntriesStorage() = storeOf<CacheStorage.HomeworkEntriesStorage>(
        getPath(CacheId.HomeworkEntries)
    )
}