package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path
import kotlinx.serialization.json.Json
import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizDTO

class ShakerQuizApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {
    suspend fun getQuizzes() : List<ShakerQuizDTO> {
        val httpRequest = httpClient.get {
            url {
                path(PATH)
            }
        }
        val data = httpRequest.bodyAsText()
        return json.decodeFromString(data)
    }

    companion object {
        const val PATH = ""
    }

}