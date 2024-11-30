package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import ru.dansh1nv.quizapi.model.rudagames.RudaGamesResponseDTO
import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizResponseDTO

class RudaGamesApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {
    //8 - Санкт-Петербург
    suspend fun getQuizzes(cityId: Int = 8) = flow {
        val httpRequest = httpClient.get {
            url {
                path(PATH + cityId)
            }
        }
        val data = httpRequest.bodyAsText()
        emit(json.decodeFromString<RudaGamesResponseDTO>(data).data.orEmpty())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "/events/dates/"
    }
}