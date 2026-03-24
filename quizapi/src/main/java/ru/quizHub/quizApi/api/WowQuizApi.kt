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
import ru.quizHub.quizApi.model.wowquiz.WowQuizResponse

class WowQuizApi(
    private val httpClient: HttpClient,
) {

    fun getGames(
        domain: String = DEFAULT_DOMAIN,
        page: Int = 1,
        upcoming: Int = 1,
    ) = flow {
        if (domain.isBlank()) {
            emit(emptyList())
            return@flow
        }
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter(PARAM_UPCOMING, upcoming)
                parameter(PARAM_PAGE, page)
                parameter(PARAM_DOMAIN, domain)
            }
        }
        val data = httpRequest.body<WowQuizResponse>()
        emit(data.data?.games.orEmpty())
    }.catch {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    companion object {
        const val PATH = "/games/all"
        const val PARAM_UPCOMING = "upcoming"
        const val PARAM_PAGE = "page"
        const val PARAM_DOMAIN = "domain"
        const val DEFAULT_DOMAIN = "https://spb.wowquiz.ru"
    }
}
