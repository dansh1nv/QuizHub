package ru.quizHub.quizApi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.quizHub.quizApi.model.quizplease.QuizPleaseResponse

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
                path(PATH, cityId.toString())
                parameter(PARAM_ORDER, ORDER_BY_DATE)
                parameter(PARAM_PAGE_SIZE, pageSize)
                parameter(PARAM_PAGE_NUMBER, pageNumber)
            }
        }
        val data = httpRequest.body<QuizPleaseResponse>()
        emit(data.data?.quizData.orEmpty())
    }.catch {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "api/games/schedule"
        const val PARAM_ORDER = "order"
        const val PARAM_PAGE_SIZE = "per_page"
        const val PARAM_PAGE_NUMBER = "page"
        const val ORDER_BY_DATE = "date"
    }
}