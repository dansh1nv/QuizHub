package ru.quizHub.quizApi.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import ru.quizHub.quizApi.model.squiz.Products
import ru.quizHub.quizApi.model.squiz.SquizDTO

class SquizApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {

    fun getQuizzes(cityId: Long?): Flow<List<SquizDTO>> = flow {
        if (cityId == null) {
            emit(emptyList())
            return@flow
        }
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter(CITY, cityId)
                parameter(PAGE_NUMBER, 1)
                parameter(PAGE_SIZE, 100)
                parameter("getparts", true)
                parameter("getoptions", true)
            }
        }
        //TODO: подумать что не так с парсингом сквиза (отличается от QP и  Shaker)
        val data = httpRequest.bodyAsText()
        emit(json.decodeFromString<Products>(data).quizGames.orEmpty())
    }.catch {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)


    companion object {
        const val PATH =
            "/api/getproductslist"
        const val PAGE_NUMBER = "slice"
        const val PAGE_SIZE = "size"
        const val CITY = "storepartuid"
    }
}


