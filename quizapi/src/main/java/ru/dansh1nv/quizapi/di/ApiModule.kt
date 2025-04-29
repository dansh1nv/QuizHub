package ru.dansh1nv.quizapi.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dansh1nv.quizapi.api.QuizPleaseApi
import ru.dansh1nv.quizapi.api.ShakerQuizApi
import ru.dansh1nv.quizapi.api.SquizApi

val QUIZ_PLEASE_KTOR = named("QUIZ_PLEASE_KTOR")
val SQUIZ_KTOR = named("SQUIZ_KTOR")
val SHAKER_QUIZ_KTOR = named("SHAKER_QUIZ_KTOR")
val WOW_QUIZ_KTOR = named("WOW_QUIZ_KTOR")

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
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                host = "store.tildacdn.com"
                url {
                    protocol = URLProtocol.HTTP
                }
            }
        }
    }

    single<HttpClient>(qualifier = QUIZ_PLEASE_KTOR) {
        val engine = HttpClientFactory().createEngine()
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                host = "quizplease.ru"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
    single<HttpClient>(qualifier = SHAKER_QUIZ_KTOR) {
        val engine = HttpClientFactory().createEngine()
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            defaultRequest {
                host = "backend.shakerquiz.ru"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }

    single<HttpClientEngine> { CIO.create() }
    single<HttpClientConfig<OkHttpConfig>> { HttpClientConfig() }
    single<SquizApi> { SquizApi(httpClient = get(qualifier = SQUIZ_KTOR), json = get()) }
    single<QuizPleaseApi> { QuizPleaseApi(httpClient = get(qualifier = QUIZ_PLEASE_KTOR), json = get()) }
    single<ShakerQuizApi> { ShakerQuizApi(httpClient = get(qualifier = SHAKER_QUIZ_KTOR), json = get()) }
}