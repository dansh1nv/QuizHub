package ru.quizHub.quizList.mappers

import ru.quizHub.common.StringDividerType
import ru.quizHub.common.formatStringsWithDividerPoints
import ru.quizHub.common.utils.localeDate.localeDay
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.CityModel
import ru.quizHub.quizList.models.item.GameDateUI
import ru.quizHub.quizList.models.item.GeoLocationUI
import ru.quizHub.quizList.models.item.LocationUI
import ru.quizHub.quizList.models.item.StatusUI
import ru.quizHub.quizList.models.item.TeamSizeUI
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.Location

class CommonMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapCities(cities: List<City>): List<CityModel> {
        return cities.map(::mapToCityModel)
    }

    fun mapToCityModel(city: City): CityModel {
        return CityModel(
            id = city.id,
            name = city.name,
            squizId = city.squizId,
            shakerQuizId = city.shakerQuizId,
            quizPleaseId = city.quizPleaseId,
            countryCode = city.countryCode,
            shakerTeamSize = city.shakerTeamSize,
            isSearchVisible = true,
            isSelected = false,
        )
    }

    fun mapToCity(city: CityModel): City {
        return City(
            id = city.id,
            name = city.name,
            squizId = city.squizId,
            shakerQuizId = city.shakerQuizId,
            quizPleaseId = city.quizPleaseId,
            countryCode = city.countryCode.orEmpty(),
            shakerTeamSize = city.shakerTeamSize
        )
    }

    fun mapToStatusUI(status: Status): StatusUI {
        return when (status) {
            Status.WRITE_TO_GAME -> StatusUI.WRITE_TO_GAME
            Status.WRITE_TO_RESERVE -> StatusUI.WRITE_TO_RESERVE
            Status.RESERVATION_CLOSE -> StatusUI.RESERVATION_CLOSE
        }
    }

    fun mapPriceAdditionalText(gameFormat: GameFormat) = when (gameFormat) {
        GameFormat.ONLINE -> resourceManager.getStringById(R.string.quiz_item_price_for_team)
        GameFormat.OFFLINE -> resourceManager.getStringById(R.string.quiz_item_price_for_people)
    }

    fun mapTeamSizeUI(minMembersCount: Int, maxMemberCount: Int): TeamSizeUI {
        return TeamSizeUI(
            minSize = minMembersCount,
            maxSize = maxMemberCount,
            teamSizeText = resourceManager.getStringById(
                R.string.team_member_text,
                minMembersCount,
                maxMemberCount
            )
        )
    }

    fun mapLocationUI(
        location: Location,
        gameFormat: GameFormat,
    ): LocationUI {
        val isOfflineGame = gameFormat == GameFormat.OFFLINE
        return LocationUI(
            place = if (isOfflineGame) {
                location.name.orEmpty()
            } else {
                resourceManager.getStringById(R.string.quiz_online)
            },
            address = location.address.takeIf { isOfflineGame }.orEmpty(),
            city = location.city.orEmpty(),
            geolocation = mapGeoLocation(
                location.latitude?.toString().orEmpty(),
                location.longitude?.toString().orEmpty(),
            ),
            isOnline = !isOfflineGame
        )
    }

    fun mapGeoLocation(
        latitude: String,
        longitude: String,
    ): GeoLocationUI {
        return GeoLocationUI(
            latitude = latitude,
            longitude = longitude,
            locationText = "${latitude},${longitude}"
        )
    }

    fun mapToGameDateUI(gameDate: GameDate): GameDateUI {
        return GameDateUI(
            date = gameDate.dateTime,
            dateText = formatStringsWithDividerPoints(
                arrayOf(gameDate.day, gameDate.month),
                StringDividerType.Space
            ),
            timeWithDay = formatStringsWithDividerPoints(
                arrayOf(gameDate.time, localeDay(gameDate.dateTime.dayOfWeek)),
                StringDividerType.PointWithSpace
            )
        )
    }

}