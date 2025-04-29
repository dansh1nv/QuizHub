package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import ru.dansh1nv.quizapi.model.squiz.Products
import ru.dansh1nv.quizapi.model.squiz.SquizDTO

class SquizApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {

    suspend fun getQuizzes(): Flow<List<SquizDTO>> = flow {
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter(CITY, 111979372401) //111979372401 - Санкт-Петербург
                parameter(PAGE_NUMBER, 1)
                parameter(PAGE_SIZE, 100)
                parameter("getparts", true)
                parameter("getoptions", true)
            }
        }
        val data = httpRequest.bodyAsText()
        emit(json.decodeFromString<Products>(data).quizGames.orEmpty())
    }.flowOn(Dispatchers.IO)


    companion object {
        const val PATH =
            "/api/getproductslist"
        const val PAGE_NUMBER = "slice"
        const val PAGE_SIZE = "size"
        const val CITY = "storepartuid"
    }
}


