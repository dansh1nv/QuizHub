package ru.dansh1nv.quiz_list_domain.models.common

enum class GameFormat(
    val id: Int,
    val alternativeId: String,
    val description: String
) {
    OFFLINE(
        id = 0,
        alternativeId = "offline",
        description = "Игры в барах"
    ),
    ONLINE(
        id = 1,
        alternativeId = "online",
        description = "Онлайн"
    );
}