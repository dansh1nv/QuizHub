package ru.dansh1nv.quiz_list_domain.models

import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.Location

data class ShakerQuiz(
    val id: String?,
    val theme: String?,
    val packageNumber: String?,
    val description: String?,
    val shortDescription: String?,
    val status: Status?,
    val eventTime: GameDate?,
    val formatTime: String?,
    val price: Int?,
    val currency: String?,
    val minMembersCount: Int?,
    val maxMembersCount: Int?,
    val location: Location? = null,
    val image: String? = null,
    val capacityStatus: String?,
): Quiz