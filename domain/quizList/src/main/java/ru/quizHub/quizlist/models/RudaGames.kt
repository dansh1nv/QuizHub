package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameType
import ru.quizHub.quizlist.models.common.Location
import ru.quizHub.quizlist.models.common.PaymentMethod

data class RudaGames(
    val id: String?,
    val title: String?,
    val description: String?,
    val image: String?,
    val gameType: GameType?,
    val formatDate: GameDate?,
    val formatTime: String?,
    val price: Int?,
    val currency: String?,
    val paymentMethod: PaymentMethod? = null,
    val location: Location?,
    val status: Status?,
    val minMembersCount: Int?,
    val maxMembersCount: Int?,
) : Quiz()
