package ru.dansh1nv.quizapi.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

class HttpClientFactory {

    fun createEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = CIO

}