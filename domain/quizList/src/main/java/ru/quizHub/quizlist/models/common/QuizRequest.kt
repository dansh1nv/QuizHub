package ru.quizHub.quizlist.models.common

data class QuizRequest(
    val cityId: CityRequest,
)

sealed class CityRequest {
    data class CityIdNumber(val id: Long): CityRequest()
    data class CityIdString(val id: String): CityRequest()
}
