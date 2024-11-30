package ru.dansh1nv.quiz_list_domain.models

import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quiz_list_domain.models.common.PaymentMethod
import ru.dansh1nv.quiz_list_domain.models.common.Status
import java.time.OffsetDateTime

data class RudaGames(
    val gameId: String?, // UUID
    val uuid: String?,
    val eventRecordId: String?,
    val gameFormat: GameFormat?,
    val gameName: String?, // Квизмашина - Батлы - Девяностые vs Нулевые #2
    val displayedGameName: String?, // Квизмашина - Батлы - Девяностые vs Нулевые #2
    val type: String?, // Девяностые vs Нулевые #2
    val time: String?,
    val price: Int?, //в копейках
    val registrationAt: OffsetDateTime?,
    val playedAt: OffsetDateTime?,
    val gameType: GameType?,
    val parentType: GameParentType?,
    val product: Product?,
    val ratingTypeId: Int?,
//    val takenTeam: Int?, //зарегестрировано команд
//    val teamCapacity: Int?, //максимум возможно
    val maxTeamPlayers: Int?,
    val paymentType: PaymentMethod?,
    val image: String?,
    val currency: Currency?,
    val isRegistrationOpened: Boolean?,
    val description: String?,
    val status: Status?,
    val location: Location?,
): Quiz()

enum class GameType(val description: String) {
    CLASSIC(description ="Классическая"),
    LITE(description = "Лайт"),
    LOTO(description = "Туц Лото"),
    MEDIA(description = "Туц Туц QUIZ"),
}

enum class GameParentType(val description: String) {
    CLASSIC(description = "Классическая 24"),
    EXCLUSIVE(description = "Эксклюзив"),
    THEMATIC(description = "Тематическая"),
    BATTLE(description = "Батлы"),
    LITE(description = "Лайт"),
}

enum class Product(
    val title: String,
    val id: Int,
) {
    MOZGOBATTLE(title = "Мозгобойня", id = 1),
    MEDIA(title = "Туц Туц Quiz", id = 3),
    QUIZ_MACHINE(title = "Квизмашина", id = 12),
    LOTO(title = "ТУЦ Лото", id = 21)
}