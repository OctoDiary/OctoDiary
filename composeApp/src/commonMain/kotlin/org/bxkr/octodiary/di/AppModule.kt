package org.bxkr.octodiary.di

import io.github.xxfast.kstore.file.extensions.storeOf
import kotlinx.io.files.Path
import org.bxkr.octodiary.data.Repository
import org.bxkr.octodiary.data.RepositoryImpl
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.auth.AuthManager
import org.bxkr.octodiary.data.auth.AuthManagerImpl
import org.bxkr.octodiary.data.auth.login.MosRuService
import org.bxkr.octodiary.data.region.MosRegionService
import org.bxkr.octodiary.data.region.MosregRegionService
import org.bxkr.octodiary.data.storageVersion
import org.bxkr.octodiary.getPaths
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.bxkr.octodiary.network.apis.MosAuthClientImpl
import org.bxkr.octodiary.ui.TopBarManager
import org.bxkr.octodiary.ui.routeFlow
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    single { TopBarManager() }
    single { MosRegionService() }
    single { MosregRegionService() }
    singleOf(::RepositoryImpl) { bind<Repository>() }
    singleOf(::AuthManagerImpl) { bind<AuthManager>() }
    singleOf(::MosAuthClientImpl) { bind<MosAuthClient>() }

    single { MosRuService() }

    viewModelOf(::AuthViewModel)
}