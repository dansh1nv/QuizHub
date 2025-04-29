package ru.dansh1nv.quiz_list_domain.models

import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType
import ru.dansh1nv.quiz_list_domain.models.common.Location

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
): Quiz