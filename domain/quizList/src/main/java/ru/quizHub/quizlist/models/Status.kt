package ru.quizHub.quizlist.models

enum class Status(
    val quizPleaseId: Int,
    val shakerId: String,
    val squizId: String,
    val rudaGamesId: String?
) {
    WRITE_TO_GAME(
        quizPleaseId = 1,
        shakerId = "PUBLISHED",
        squizId = "Запись на игру",
        rudaGamesId = "registration"
    ),
    WRITE_TO_RESERVE(
        quizPleaseId = 2,
        shakerId = "IS_RESERVE",
        squizId = "Запись в резерв",
        rudaGamesId = "reserve"
    ),
    RESERVATION_CLOSE(
        quizPleaseId = 3,
        shakerId = "CLOSED",
        squizId = "Регистрация закрыта",
        rudaGamesId = null
    );
}