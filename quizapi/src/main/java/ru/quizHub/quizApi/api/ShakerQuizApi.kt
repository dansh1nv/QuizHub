package ru.quizHub.quizApi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.quizHub.quizApi.model.shakerquiz.ShakerQuizResponseDTO

class ShakerQuizApi(
    private val httpClient: HttpClient
) {
    fun getQuizzes(cityId: String?) = flow {
        if (cityId == null) {
            emit(emptyList())
            return@flow
        }
        val response = httpClient.get {
            url {
                path(PATH)
                parameter(PAGE_NUMBER, 1)
                parameter(PAGE_SIZE, 100)
                parameter(
                    SEARCH,
                    "{\"$CITY_ID\":[\"$cityId\"]}"
                )
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