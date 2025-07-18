package ru.dansh1nv.quiz.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    val cities: List<CityDTO>,
)

@Serializable
data class CityDTO(
    val id: Long,
    val name: String? = null,
    val quizPleaseId: Long? = null,
    val shakerQuizId: String? = null,
    val squizId: Long? = null,
    val shakerTeamSize: TeamSizeDTO? = null,
    val country: String? = null,
)

@Serializable
data class TeamSizeDTO(
    val maximum: Int,
    val minimum: Int,
)