package ru.dansh1nv.quiz_list_domain.models

import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quiz_list_domain.models.common.PaymentMethod
import ru.dansh1nv.quiz_list_domain.models.common.Status

data class QuizPlease(
    val id: String?,
    val title: String?,
    val packageNumber: String?,
    val description: String?,
    val image: String?,
    val gameFormat: GameFormat?,
    val datetime: String?,
    val formatDate: GameDate?,
    val formatTime: String?,
    val price: Int?,
    val formatPrice: String?,
    val location: Location?,
    val difficulty: String?, //<div class=\"badge-difficulty__title badge-difficulty__title_registration\"
    val status: Status?,
    val paymentMethod: PaymentMethod?,
) : Quiz()
