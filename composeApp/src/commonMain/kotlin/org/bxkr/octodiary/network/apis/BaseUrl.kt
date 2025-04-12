package org.bxkr.octodiary.network.apis

import org.bxkr.octodiary.Region

class BaseUrl(
    val mainSchoolApi: String,
    val dSchoolApi: String,
    val secondaryApi: String
) {
    companion object {
        fun getInstance(region: Region): BaseUrl {
            return when (region) {
                Region.Moscow -> BaseUrl(
                    "https://school.mos.ru/api/",
                    "https://dnevnik.mos.ru/",
                    "https://school.mos.ru/"
                )
                Region.Suburb -> BaseUrl(
                    "https://authedu.mosreg.ru/api/",
                    "https://myschool.mosreg.ru/",
                    "https://authedu.mosreg.ru/"
                )
                Region.Kaluga -> TODO()
                Region.Tatarstan -> TODO()
                Region.Dagestan -> TODO()
                Region.Tumen -> TODO()
                Region.Chechnya -> TODO()
                Region.Yamal -> TODO()
            }
        }
    }
}