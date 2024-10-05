package ru.dansh1nv.quiz_list_domain.models.quizPlease

enum class Status(
    val id: Int,
    val title: String,
) {
    WRITE_TO_GAME(
        id = 1,
        title = "Записаться на игру"
    ),
    WRITE_TO_RESERVE(
        id = 2,
        title = "Записаться в резерв"
    ),
    RESERVATION_CLOSE(
        id = 3,
        title = "Регистрация закрыта"
    );
}