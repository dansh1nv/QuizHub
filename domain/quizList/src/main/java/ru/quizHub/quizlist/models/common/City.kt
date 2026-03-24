package ru.quizHub.quizlist.models.common

data class City(
    val id: Long,
    val name: String,
    val squizId: Long?,
    val quizPleaseId: Long?,
    val rudaGamesId: Int?,
    val shakerQuizId: String?,
    val shakerTeamSize: ShakerTeamSize?,
    val countryCode: String,
    val wowQuizDomain: String? = null,
)

data class ShakerTeamSize(
    val maximum: Int,
    val minimum: Int,
)
