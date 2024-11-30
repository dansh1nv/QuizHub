package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz_list_domain.models.Currency
import ru.dansh1nv.quiz_list_domain.models.GameParentType
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.Product
import ru.dansh1nv.quiz_list_domain.models.RudaGames
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quiz_list_domain.models.common.PaymentMethod
import ru.dansh1nv.quiz_list_domain.models.common.Status
import ru.dansh1nv.quizapi.model.rudagames.EventDTO

class RudaGamesMapper {

    fun mapToRudaGames(events: List<EventDTO>): List<RudaGames> {
        return events.map(::mapToRudaGame)
    }

    private fun mapToRudaGame(event: EventDTO): RudaGames {
        return RudaGames(
            gameId = event.gameId,
            uuid = event.uuid,
            eventRecordId = event.eventRecordId,
            gameFormat = GameFormat.entries.firstOrNull { it.alternativeId == event.distributionFormat },
            gameName = event.gameName,
            displayedGameName = event.displayedGameName,
            type = event.type,
            time = event.time,
            price = event.price?.let(::convertToRubles),
            registrationAt = event.registrationAt,
            playedAt = event.playedAt,
            gameType = GameType.entries.firstOrNull { it.description == event.gameType },
            parentType = GameParentType.entries.firstOrNull { it.description == event.parentType },
            product = Product.entries.firstOrNull {
                it.title == event.product || it.id == event.productId
            },
            ratingTypeId = event.ratingTypeId,
            maxTeamPlayers = event.maxTeamPlayers,
            paymentType = PaymentMethod.entries.firstOrNull { it.rudaId == event.paymentType },
            image = event.mediaBanner?.head,
            currency = Currency.entries.firstOrNull {
                it.name.lowercase() == event.currency?.lowercase()
            },
            isRegistrationOpened = event.isRegistrationOpened,
            description = event.description,
            status = Status.entries.firstOrNull {
                it.rudaGamesId == event.linkStatus
            },
            location = mapToLocation(
                place = event.place.orEmpty(),
                address = event.address.orEmpty(),
                cityLink = event.city.orEmpty(),
            )
        )
    }

    private fun mapToLocation(
        place: String,
        address: String,
        cityLink: String, // sankt-peterburg
    ) : Location {
        return Location(
            name = place,
            address = address,
            latitude = null,
            longitude = null,
            city = cityLink,
        )
    }

    private fun convertToRubles(price: Int): Int {
        return price / 100
    }
}