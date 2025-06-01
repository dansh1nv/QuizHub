package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizResponseDTO

class ShakerQuizApi(
    private val httpClient: HttpClient
) {
    fun getQuizzes() = flow {
        val response = httpClient.get {
            url {
                path(PATH)
                parameter(PAGE_NUMBER, 1)
                parameter(PAGE_SIZE, 100)
                parameter(
                    SEARCH,
                    "{\"$CITY_ID\":[\"b489621b-cfb2-4aef-8c22-02daf19fa08f\"]}"
                ) //b489621b-cfb2-4aef-8c22-02daf19fa08f - id Санкт-Петербург
            }
        }
        val data = response.body<ShakerQuizResponseDTO>()
        emit(data.data?.items.orEmpty())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "/api/v1/event/published"
        private const val CITY_ID = "city_id"
        private const val PAGE_NUMBER = "page"
        private const val PAGE_SIZE = "size"
        private const val SEARCH = "search"
    }

}