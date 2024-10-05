package ru.dansh1nv.quiz_list_domain.models.squiz

import ru.dansh1nv.quiz_list_domain.models.Quiz

data class SQuiz(
    val id: Long?,
    val city: String?,
    val gameDate: GameDate?,
    val type: GameType?,
    val format: GameFormat?,
    val theme: String?,
    val packageNumber: String?,
    val description: String?,
    val additionDescription: String?,
    val image: String?,
    val location: String?,
    val address: String?,
    val price: String?,
    val status: String?,
    val organization: String?,
): Quiz