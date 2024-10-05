package ru.dansh1nv.quiz_list_domain.models.squiz

enum class GameFormat(val description: String) {
    ONLINE("Онлайн"),
    OFFLINE("Игры в барах"),
    UNKNOWN("Неизвестный формат");
}