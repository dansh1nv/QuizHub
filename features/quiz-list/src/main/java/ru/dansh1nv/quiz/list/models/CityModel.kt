package ru.dansh1nv.quiz.list.models

data class CityModel(
    val id: Long,
    val name: String,
    val squizId: Long?,
    val quizPleaseId: Long?,
    val shakerQuizId: String?,
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
        )
    }
}
