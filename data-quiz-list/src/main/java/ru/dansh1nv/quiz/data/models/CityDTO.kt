package ru.dansh1nv.quiz.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    val cities: List<CityDTO>,
)

@Serializable
data class CityDTO(
    val id: Long,
    val name: String?,
    val quizPleaseId: Long?,
    val shakerQuizId: String?,
    val squizId: Long?,
    val shakerTeamSize: TeamSizeDTO?,
    val country: String?,
)

@Serializable
data class TeamSizeDTO(
    val maximum: Int,
    val minimum: Int,
)