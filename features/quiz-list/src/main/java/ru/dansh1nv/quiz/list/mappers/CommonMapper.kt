package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.GeoLocationUI
import ru.dansh1nv.quiz.list.models.item.LocationUI
import ru.dansh1nv.quiz.list.models.item.StatusUI
import ru.dansh1nv.quiz.list.models.item.TeamSizeUI
import ru.dansh1nv.quiz_list_domain.models.Status
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.Location

class CommonMapper(
    private val resourceManager: IResourceManager,
) {

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
        else -> ""
    }

    fun mapTeamSizeUI(minMembersCount: Int, maxMemberCount: Int): TeamSizeUI {
        return TeamSizeUI(
            minSize = minMembersCount,
            maxSize = maxMemberCount,
            teamSizeText = "$minMembersCount-$maxMemberCount участников"
        )
    }

    fun mapLocationUI(location: Location): LocationUI {
        return LocationUI(
            place = location.name.orEmpty(),
            address = location.address.orEmpty(),
            city = location.city.orEmpty(),
            geolocation = mapGeoLocation(
                location.latitude?.toString().orEmpty(),
                location.longitude?.toString().orEmpty(),
            )
        )
    }

    private fun mapGeoLocation(
        latitude: String,
        longitude: String,
    ): GeoLocationUI {
        return GeoLocationUI(
            latitude = latitude,
            longitude = longitude,
            locationText = "${latitude},${longitude}"
        )
    }

}