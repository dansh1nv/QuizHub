package ru.dansh1nv.quiz.list.models

import ru.dansh1nv.quiz_list_domain.models.squiz.GameFormat
import ru.dansh1nv.quiz_list_domain.models.squiz.GameType

data class SQuizUI(
    val id: Long,
    val city: String,
    val type: GameType,
    val format: GameFormat,
    val theme: String,
    val status: String,
    val packageNumber: String,
    val description: String,
    val additionDescription: String,
    val image: String,
    val location: String,
    val gameDate: GameDateUI,
    val address: String,
    val price: String,
    val organization: String,
) : QuizUI()