package ru.quizHub.quizList.models

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
    val rudaGamesId: Int? = null,
    val shakerQuizId: String? = null,
    val squizId: Long? = null,
    val shakerTeamSize: TeamSizeDTO? = null,
    val country: String? = null,
    val wowQuizDomain: String? = null,
    val smuziStorePartId: Long? = null,
)

@Serializable
data class TeamSizeDTO(
    val maximum: Int,
    val minimum: Int,
)