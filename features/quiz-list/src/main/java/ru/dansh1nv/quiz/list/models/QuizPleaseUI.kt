package ru.dansh1nv.quiz.list.models

data class QuizPleaseUI(
    val id: Long,
    val theme: String,
    val packageNumber: String,
    val description: String,
    val image: String,
    val gameFormat: String,
    override val gameDate: GameDateUI,
    val formatPrice: String,
    val place: String,
    val address: String,
    val city: String,
    val location: Location,
    val difficulty: String,
    val status: String,
    val paymentMethod: String,
) : QuizUI(gameDate)

sealed class QuizUI(open val gameDate: GameDateUI)

//data class SQuizUI(
//    val id: Long,
//    val theme: String,
//    val packageNumber: String,
//    val description: String,
//    val image: String,
//    val format: GameFormat,
//    override val gameDate: GameDateUI,
//    val type: GameType,
//    val price: String,
//    val city: String,
//    val status: String,
//    val additionDescription: String,
//    val location: String,
//    val address: String,
//    val priceAdditionalText: String,
//    val organization: String,
//) : QuizUI(gameDate)