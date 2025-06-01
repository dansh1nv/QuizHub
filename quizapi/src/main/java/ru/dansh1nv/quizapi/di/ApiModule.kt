package ru.dansh1nv.quizapi.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttpConfig
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
import ru.dansh1nv.quizapi.api.GeocodingService
import ru.dansh1nv.quizapi.Urls.BASE_QUIZ_PLEASE_URL
import ru.dansh1nv.quizapi.Urls.BASE_SHAKER_URL
import ru.dansh1nv.quizapi.Urls.BASE_SQUIZ_URL
import ru.dansh1nv.quizapi.api.QuizPleaseApi
import ru.dansh1nv.quizapi.api.ShakerQuizApi
import ru.dansh1nv.quizapi.api.SquizApi

val QUIZ_PLEASE_KTOR = named("QUIZ_PLEASE_KTOR")
val SQUIZ_KTOR = named("SQUIZ_KTOR")
val SHAKER_QUIZ_KTOR = named("SHAKER_QUIZ_KTOR")
val WOW_QUIZ_KTOR = named("WOW_QUIZ_KTOR")
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
        val engine = HttpClientFactory().createEngine()
        HttpClient(engine) {
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
        val engine = HttpClientFactory().createEngine()
        HttpClient(engine) {
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
    single<HttpClient>(qualifier = SHAKER_QUIZ_KTOR) {
        val engine = HttpClientFactory().createEngine()
        HttpClient(engine) {
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
    single<HttpClient>(qualifier = GEO_SERVICE) {
        HttpClient(CIO) {
            install(HttpTimeout) {
                connectTimeoutMillis = 10_000
                requestTimeoutMillis = 20_000
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

    single<HttpClientEngine> { CIO.create() }
    single<HttpClientConfig<OkHttpConfig>> { HttpClientConfig() }
    single<SquizApi> { SquizApi(httpClient = get(qualifier = SQUIZ_KTOR), get()) }
    single<QuizPleaseApi> { QuizPleaseApi(httpClient = get(qualifier = QUIZ_PLEASE_KTOR))}
    single<ShakerQuizApi> { ShakerQuizApi(httpClient = get(qualifier = SHAKER_QUIZ_KTOR)) }
    single<GeocodingService> { GeocodingService(client = get(qualifier = GEO_SERVICE)) }
}