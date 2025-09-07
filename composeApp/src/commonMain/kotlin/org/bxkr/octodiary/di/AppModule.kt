package org.bxkr.octodiary.di

import io.github.xxfast.kstore.file.extensions.storeOf
import kotlinx.io.files.Path
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.StorageV1
import org.bxkr.octodiary.data.gateway.MesMosAuthGateway
import org.bxkr.octodiary.data.storageVersion
import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.domain.model.auth.AuthInfo
import org.bxkr.octodiary.getPaths
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [NetworkModule::class])
@ComponentScan("org.bxkr.octodiary")
class AppModule {
    @Single
    fun storage() = storeOf<StorageLatest>(
        file = Path("${getPaths().files}/main"),
        version = storageVersion,
        default = StorageLatest()
    )

    @Single
    fun authInfo(
        availableGateways: List<AuthGateway>
    ) = AuthInfo(mapOf(
        DiaryId.MesMos to availableGateways.first { it is MesMosAuthGateway }
    ))
}