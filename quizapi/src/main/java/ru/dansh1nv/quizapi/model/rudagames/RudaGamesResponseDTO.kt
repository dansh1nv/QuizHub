package ru.dansh1nv.quizapi.model.rudagames
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.dansh1nv.quizapi.serializer.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class RudaGamesResponseDTO(
    val data: List<EventDTO>? = null,
)

@Serializable
data class EventDTO(
    val uuid: String? = null,
    @SerialName("event_record_id")
    val eventRecordId: String? = null,
    val place: String? = null,
    @SerialName("distribution_format")
    val distributionFormat: String? = null, // offline
    @SerialName("game_name")
    val gameName: String? = null, // Квизмашина - Батлы - Девяностые vs Нулевые #2
    @SerialName("displayed_game_name")
    val displayedGameName: String? = null, // Квизмашина - Батлы - Девяностые vs Нулевые #2
    val address: String? = null,
    val type: String? = null, // Девяностые vs Нулевые #2
    val time: String? = null,
    val price: Int? = null, //в копейках
    @SerialName("registration_at")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val registrationAt: OffsetDateTime? = null, // 2024-10-26T12:00:00
    @SerialName("played_at")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val playedAt: OffsetDateTime? = null, // 2024-11-30T19:30:00
    @SerialName("game_type")
    val gameType: String? = null, // Туц Туц QUIZ, Лайт, Туц Лото, Классическая
    @SerialName("parent_type")
    val parentType: String? = null, //Классическая 24, Тематическая, Батлы, Эксклюзив, Лайт
    val product: String? = null, //Мозгобойня, Туц Туц Quiz, Квизмашина, ТУЦ Лото
    @SerialName("product_id")
    val productId: Int? = null, // 1 - Мозгобойня, 3 - Туц Туц Quiz, 12 - Квизмашина, 21 - ТУЦ Лото
    @SerialName("rating_type_id")
    val ratingTypeId: Int? = null,
    @SerialName("taken_team")
    val takenTeam: Int? = null, //зарегестрировано команд
    @SerialName("team_capacity")
    val teamCapacity: Int? = null, //максимум возможно
    @SerialName("max_team_players")
    val maxTeamPlayers: Int? = null,
    @SerialName("payment_type")
    val paymentType: String? = null, // Оплата наличными и картой
    @SerialName("media_banner")
    val mediaBanner: MediaBanner? = null, //Содержит картинку для превью
    val currency: String? = null, // RUB
    @SerialName("is_registration_opened")
    val isRegistrationOpened: Boolean? = null,
    @SerialName("is_privileged_registration_opened")
    val isPrivilegedRegistrationOpened: Boolean? = null,
    val gameId: String? = null, // UUID
    val description: String? = null,
    val linkStatus: String? = null, //reserve, registration
    @SerialName("city_link_name")
    val city: String? = null, // sankt-peterburg
)

@Serializable
data class MediaBanner(
    val head: String
)