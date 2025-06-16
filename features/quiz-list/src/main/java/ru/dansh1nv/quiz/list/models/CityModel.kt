package ru.dansh1nv.quiz.list.models

import ru.dansh1nv.quiz_list_domain.models.common.CityId

data class CityModel(
    val name: String,
    val id: CityId,
    val isSearchVisible: Boolean,
    val isSelected: Boolean,
) {
    companion object {
        val UNKNOWN = CityModel(
            name = "Выберите город",
            id = CityId.UNKNOWN,
            isSearchVisible = false,
            isSelected = true,
        )
    }
}
