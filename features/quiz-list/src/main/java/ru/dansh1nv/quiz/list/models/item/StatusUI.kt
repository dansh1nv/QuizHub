package ru.dansh1nv.quiz.list.models.item

enum class StatusUI(
    val title: String,
) {
    WRITE_TO_GAME(
        title = "Регистрация открыта"
    ),
    WRITE_TO_RESERVE(
        title = "Запись в резерв"
    ),
    RESERVATION_CLOSE(
        title = "Регистрация закрыта"
    );
}