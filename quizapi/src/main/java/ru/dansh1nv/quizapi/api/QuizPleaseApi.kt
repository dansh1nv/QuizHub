package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseDTO
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseData
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseResponse

class QuizPleaseApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {

    suspend fun getQuizzes() = flow {
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter("city_id", 17) //17 - Санкт-Петербург
            }
        }
        val data = httpRequest.bodyAsText()
        emit(json.decodeFromString<QuizPleaseResponse>(data).data?.quizData.orEmpty())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "/api/game"
    }
}