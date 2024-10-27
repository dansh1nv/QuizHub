package ru.dansh1nv.quiz_list_domain.models

enum class Status(
    val quizPleaseId: Int,
    val shakerId: String,
    val squizId: String,
) {
    WRITE_TO_GAME(
        quizPleaseId = 1,
        shakerId = "PUBLISHED",
        squizId = "Запись на игру",

    ),
    WRITE_TO_RESERVE(
        quizPleaseId = 2,
        shakerId = "IS_RESERVE",
        squizId = "",
    ),
    RESERVATION_CLOSE(
        quizPleaseId = 3,
        shakerId = "CLOSED",
        squizId = "",
    );
}