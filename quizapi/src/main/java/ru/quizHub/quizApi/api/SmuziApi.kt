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
import ru.quizHub.quizApi.model.smuzi.SmuziProductDTO
import ru.quizHub.quizApi.model.smuzi.SmuziProductsResponse
import java.util.logging.Level
import java.util.logging.Logger

class SmuziApi(
    private val httpClient: HttpClient,
    private val json: Json,
) {

    fun getQuizzes(storePartId: Long?): Flow<List<SmuziProductDTO>> = flow {
        if (storePartId == null) {
            emit(emptyList())
            return@flow
        }
        val httpRequest = httpClient.get {
            url {
                path(PATH)
                parameter(STORE_PART_UID, storePartId)
                parameter(SLICE, 1)
                parameter(SIZE, 100)
                parameter(GET_PARTS, true)
                parameter(GET_OPTIONS, true)
            }
        }
        // Tilda отдаёт JSON с Content-Type: text/html
        val data = httpRequest.bodyAsText()
        emit(json.decodeFromString<SmuziProductsResponse>(data).products.orEmpty())
    }.catch { throwable ->
        logger.log(
            Level.SEVERE,
            "SmuziApi: Failed to load quizzes for storePartId=$storePartId",
            throwable,
        )
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    companion object {
        private val logger = Logger.getLogger(SmuziApi::class.java.name)
        const val PATH = "/api/getproductslist/"
        const val STORE_PART_UID = "storepartuid"
        const val SLICE = "slice"
        const val SIZE = "size"
        const val GET_PARTS = "getparts"
        const val GET_OPTIONS = "getoptions"
    }
}