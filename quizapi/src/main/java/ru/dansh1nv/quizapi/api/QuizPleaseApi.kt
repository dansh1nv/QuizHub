package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseResponse

class QuizPleaseApi(
    private val httpClient: HttpClient
) {

    fun getQuizzes(
        cityId: Long?,
        pageNumber: Int = 1,
        pageSize: Int = 100,
    ) = flow {
        if (cityId == null) {
            emit(emptyList())
            return@flow
        }
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter(PARAM_CITY_ID, cityId)
                parameter(PARAM_PAGE_NUMBER, pageNumber)
                parameter(PARAM_PAGE_SIZE, pageSize)
            }
        }
        val data = httpRequest.body<QuizPleaseResponse>()
        emit(data.data?.quizData.orEmpty())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "/api/game"
        const val PARAM_CITY_ID = "city_id"
        const val PARAM_PAGE_NUMBER = "page"
        const val PARAM_PAGE_SIZE = "per-page"
    }
}