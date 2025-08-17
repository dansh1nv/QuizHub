package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType
import ru.quizHub.quizlist.models.common.Location

data class SQuiz(
    val id: String?,
    val gameDate: GameDate?,
    val type: GameType?,
    val format: GameFormat?,
    val theme: String?,
    val packageNumber: String?,
    val description: String?,
    val additionDescription: String?,
    val image: String?,
    val price: String?,
    val location: Location?,
    val status: Status?,
    val difficult: String?,
): Quiz()