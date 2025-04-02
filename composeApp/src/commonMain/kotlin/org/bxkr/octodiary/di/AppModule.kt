package org.bxkr.octodiary.di

import io.github.xxfast.kstore.file.extensions.storeOf
import kotlinx.io.files.Path
import org.bxkr.octodiary.data.Repository
import org.bxkr.octodiary.data.RepositoryImpl
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.auth.AuthManager
import org.bxkr.octodiary.data.auth.AuthManagerImpl
import org.bxkr.octodiary.data.storageVersion
import org.bxkr.octodiary.getPaths
import org.bxkr.octodiary.network.MosAuthClient
import org.bxkr.octodiary.network.MosAuthClientImpl
import org.bxkr.octodiary.ui.routeFlow
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        storeOf<StorageLatest>(
            file = Path("${getPaths().files}/main"),
            version = storageVersion,
            default = StorageLatest()
        )
    }
    single { routeFlow() }
    singleOf(::RepositoryImpl) { bind<Repository>() }
    singleOf(::AuthManagerImpl) { bind<AuthManager>() }
    singleOf(::MosAuthClientImpl) { bind<MosAuthClient>() }
}