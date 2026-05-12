package org.bxkr.octodiary.di.module

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.bxkr.octodiary.network.config.AuthEduConfig
import org.bxkr.octodiary.network.config.MosruApiConfig
import org.bxkr.octodiary.network.config.UchebnikConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {
    @Single
    fun getHttpClient() = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("KtorLogger: $message")
                }
            }
            level = LogLevel.INFO
        }
    }

    @Single
    fun getMosruApiConfig() = MosruApiConfig(
        "https://login.mos.ru/",
        "https://school.mos.ru/v3/auth/sudir/auth"
    )

    @Single
    fun getUchebnikConfig() = UchebnikConfig(
        "https://uchebnik.mos.ru/"
    )

    @Single
    fun getAuthEduConfig() = AuthEduConfig(
        "https://authedu.mosreg.ru/"
    )
}