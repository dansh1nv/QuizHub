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
import ru.quizHub.quizApi.model.rudagames.RudaGamesDTO

class RudaGamesApi(
    private val httpClient: HttpClient
) {
    fun getQuizzes(cityId: Int?) = flow {
        if (cityId == null) {
            emit(emptyList())
            return@flow
        }
        val response = httpClient.get {
            url {
                path("$EVENTS_PATH/$cityId")
                parameter(PARAM_SORT, "played_at")
            }
        }
        val data = response.body<List<RudaGamesDTO>>()
        emit(data)
    }.catch {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val EVENTS_PATH = "/events/dates"
        const val PARAM_SORT = "sort"
    }
}