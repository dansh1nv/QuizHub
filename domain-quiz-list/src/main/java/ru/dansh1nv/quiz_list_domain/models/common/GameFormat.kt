package ru.dansh1nv.quiz_list_domain.models.common

enum class GameFormat(
    val id: Int,
    val description: String
) {
    OFFLINE(
        id = 0,
        description = "Игры в барах"
    ),
    ONLINE(
        id = 1,
        description = "Онлайн"
    );
}