package ru.quizHub.quizApi.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.quizHub.quizApi.Urls.BASE_QUIZ_PLEASE_URL
import ru.quizHub.quizApi.Urls.BASE_RUDA_GAMES_URL
import ru.quizHub.quizApi.Urls.BASE_SHAKER_URL
import ru.quizHub.quizApi.Urls.BASE_SMUZI_URL
import ru.quizHub.quizApi.Urls.BASE_SQUIZ_URL
import ru.quizHub.quizApi.Urls.BASE_WOW_QUIZ_URL
import ru.quizHub.quizApi.api.GeocodingService
import ru.quizHub.quizApi.api.QuizPleaseApi
import ru.quizHub.quizApi.api.RudaGamesApi
import ru.quizHub.quizApi.api.ShakerQuizApi
import ru.quizHub.quizApi.api.SmuziApi
import ru.quizHub.quizApi.api.SquizApi
import ru.quizHub.quizApi.api.WowQuizApi

val QUIZ_PLEASE_KTOR = named("QUIZ_PLEASE_KTOR")
val SQUIZ_KTOR = named("SQUIZ_KTOR")
val SHAKER_QUIZ_KTOR = named("SHAKER_QUIZ_KTOR")
val WOW_QUIZ_KTOR = named("WOW_QUIZ_KTOR")
val RUDA_GAMES_KTOR = named("RUDA_GAMES_KTOR")
val SMUZI_KTOR = named("SMUZI_KTOR")
val GEO_SERVICE = named("GEO_SERVICE")

fun apiModule() = module {

    single<HttpClientFactory> { HttpClientFactory() }
    single<Json> {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    single<HttpClient>(qualifier = SQUIZ_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = BASE_SQUIZ_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = QUIZ_PLEASE_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_QUIZ_PLEASE_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = WOW_QUIZ_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_WOW_QUIZ_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = SHAKER_QUIZ_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_SHAKER_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = RUDA_GAMES_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_RUDA_GAMES_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = SMUZI_KTOR) {
        HttpClient(get<HttpClientFactory>().createEngine()) {
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_SMUZI_URL
                }
            }
        }
    }
    single<HttpClient>(qualifier = GEO_SERVICE) {
        HttpClient(CIO) {
            install(HttpTimeout) {
                connectTimeoutMillis = 10_000
                requestTimeoutMillis = 20_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) { json(get()) }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "nominatim.openstreetmap.org"
                }
                header("User-Agent", "QuizHub")
            }
        }
    }

    single<SquizApi> { SquizApi(httpClient = get(qualifier = SQUIZ_KTOR), get()) }
    single<QuizPleaseApi> { QuizPleaseApi(httpClient = get(qualifier = QUIZ_PLEASE_KTOR)) }
    single<WowQuizApi> { WowQuizApi(httpClient = get(qualifier = WOW_QUIZ_KTOR)) }
    single<ShakerQuizApi> { ShakerQuizApi(httpClient = get(qualifier = SHAKER_QUIZ_KTOR)) }
    single<RudaGamesApi> { RudaGamesApi(httpClient = get(qualifier = RUDA_GAMES_KTOR)) }
    single<SmuziApi> { SmuziApi(httpClient = get(qualifier = SMUZI_KTOR), json = get()) }
    single<GeocodingService> { GeocodingService(client = get(qualifier = GEO_SERVICE)) }
}
