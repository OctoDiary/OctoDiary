package org.bxkr.octodiary.data.datasource.remote.impl

import org.bxkr.octodiary.data.datasource.remote.MesLikeRemoteDataSource
import org.bxkr.octodiary.data.datasource.remote.MesMosRegRemoteDataSource
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.network.UchebnikApiService
import org.bxkr.octodiary.network.config.SchoolMesApiConfig
import org.koin.core.annotation.Single

@Single
class MesMosRegRemoteDataSource(
    uchebnikApiService: UchebnikApiService
) : MesLikeRemoteDataSourceImpl(
    DiaryId.MesMosReg,
    SchoolMesApiConfig("https://myschool.mosreg.ru/"),
    uchebnikApiService
), MesMosRegRemoteDataSource {

}