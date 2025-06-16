package ru.dansh1nv.quiz_list_domain.models.common

data class City(
    val id: CityId,
    val name: String,
)

enum class CityId(val title: String) {
    MOSCOW(title = "Москва"),
    SPB(title = "Санкт-Петербург"),
    KRASNODAR(title = "Краснодар"),
    UNKNOWN(title = "Выберите город"),
}
