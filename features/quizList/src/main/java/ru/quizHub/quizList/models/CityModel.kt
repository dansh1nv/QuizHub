package ru.quizHub.quizList.models

import ru.quizHub.quizlist.models.common.ShakerTeamSize

data class CityModel(
    val id: Long,
    val name: String,
    val squizId: Long?,
    val quizPleaseId: Long?,
    val rudaGamesId: Int?,
    val shakerQuizId: String?,
    val countryCode: String?,
    val shakerTeamSize: ShakerTeamSize?,
    val isSearchVisible: Boolean,
    val isSelected: Boolean,
) {
    companion object {
        val UNKNOWN = CityModel(
            id = -1,
            name = "Выберите город",
            isSearchVisible = false,
            isSelected = true,
            squizId = null,
            shakerQuizId = null,
            quizPleaseId = null,
            rudaGamesId = null,
            countryCode = "RU",
            shakerTeamSize = null,
        )
    }
}
