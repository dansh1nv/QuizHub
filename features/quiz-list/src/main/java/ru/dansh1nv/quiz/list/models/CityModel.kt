package ru.dansh1nv.quiz.list.models

import ru.dansh1nv.quiz_list_domain.models.common.ShakerTeamSize

data class CityModel(
    val id: Long,
    val name: String,
    val squizId: Long?,
    val quizPleaseId: Long?,
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
            countryCode = "RU",
            shakerTeamSize = null,
        )
    }
}
