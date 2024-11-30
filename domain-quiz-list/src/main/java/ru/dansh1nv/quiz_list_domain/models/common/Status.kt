package ru.dansh1nv.quiz_list_domain.models.common

enum class Status(
    val quizPleaseId: Int,
    val shakerId: String,
    val squizId: String,
    val rudaGamesId: String,
) {
    //TODO:Определить недостающие id для squiz и rudaGames
    WRITE_TO_GAME(
        quizPleaseId = 1,
        shakerId = "PUBLISHED",
        squizId = "Запись на игру",
        rudaGamesId = "registration"
    ),
    WRITE_TO_RESERVE(
        quizPleaseId = 2,
        shakerId = "IS_RESERVE",
        squizId = "",
        rudaGamesId = "reserve"
    ),
    RESERVATION_CLOSE(
        quizPleaseId = 3,
        shakerId = "CLOSED",
        squizId = "",
        rudaGamesId = ""
    );
}