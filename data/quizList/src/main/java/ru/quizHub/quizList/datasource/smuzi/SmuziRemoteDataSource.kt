package ru.quizHub.quizList.datasource.smuzi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.quizHub.quizApi.api.SmuziApi
import ru.quizHub.quizApi.model.smuzi.SmuziProductDTO

class SmuziRemoteDataSource(
    private val api: SmuziApi,
) {
    fun getQuizList(storePartId: Long?): Flow<List<SmuziProductDTO>> =
        if (storePartId == null) {
            flowOf(emptyList())
        } else {
            api.getQuizzes(storePartId = storePartId)
        }
}