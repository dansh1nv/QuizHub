package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz.data.Constants
import ru.dansh1nv.quiz.data.utils.DayOfTheWeekUtils
import ru.dansh1nv.quiz_list_domain.models.GameDate
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quizapi.model.squiz.Characteristic
import ru.dansh1nv.quizapi.model.squiz.SquizDTO
import java.time.LocalDate
import java.time.LocalDateTime

object SquizMapper {

    fun map(quizzes: List<SquizDTO>): List<SQuiz> {
        return quizzes.mapNotNull(::mapToQuiz)
    }

    private fun mapToQuiz(quiz: SquizDTO): SQuiz? {
        quiz.characteristics?.let {
            it.size >= 2
        } ?: return null
        val gameFormat = getGameFormat((quiz.characteristics as List<Characteristic>))
        val gameType = getGameType((quiz.characteristics as List<Characteristic>))
        val text = quiz.text.orEmpty()
        val (date, time) = quiz.title?.split("::") ?: return null
        val (dayWithMonth, dayOfTheWeek) = date.trim().split(",", limit = 2)
        val (day, month) = dayWithMonth.trim().split(" ", limit = 2)
        val price = quiz.price?.split(".")?.first()?.toBigDecimal()
        val address = Regex(""";">(.*?)</a>""").findAll(text)
            .map {
                it.groupValues[1]
                    .replace("\"", "")
                    .replace("ул. ", "")
            }
            .toList()
        val theme = Regex("""Описание: "(.*?)"""").find(text)?.groupValues?.getOrNull(1)
        val description = Regex("""Текст карточки: "(.*?)"""").find(text)?.groupValues?.getOrNull(1)
        val additionDescription =
            Regex("""Дополнение описания: "(.*?)"""").find(text)?.groupValues?.getOrNull(1)

        val packageNumber = quiz.packageNumber.orEmpty()
        val status = Regex("""SMS: "(.*?)"""").find(text)?.groupValues?.getOrNull(1)
        val img = quiz.galleryImage
            ?.substringAfter(":\"")
            ?.dropLast(3)
            ?.replace("\\/", "\\")
        return SQuiz(
            id = quiz.id,
            city = quiz.cityId.orEmpty(),
            gameDate = mapGameDate(
                day = day,
                month = month,
                dayOfTheWeek = dayOfTheWeek,
                time = time,
                dayWithMonth = dayWithMonth,
            ),
            type = gameType,
            format = gameFormat,
            theme = theme,
            packageNumber = packageNumber,
            description = description,
            additionDescription = additionDescription,
            image = img,
            location = address.getOrNull(0) ?: "Онлайн",
            address = address.getOrNull(1).orEmpty(),
            price = price.toString(),
            status = status,
            organization = "SQUIZ",
        )
    }

    private fun getGameFormat(characteristics: List<Characteristic>): GameFormat? {
        return characteristics.firstOrNull { characteristic ->
            characteristic.title == Constants.GAME_FORMAT
        }?.run {
            GameFormat.entries.firstOrNull { gameFormat -> gameFormat.description == value }
        }
    }

    private fun getGameType(characteristics: List<Characteristic>): GameType? {
        return characteristics.firstOrNull { characteristic ->
            characteristic.title == Constants.GAME_TYPE
        }?.run {
            GameType.entries.firstOrNull { gameType -> gameType.description == value }
        }
    }

    private fun mapGameDate(
        day: String,
        month: String,
        dayOfTheWeek: String,
        time: String,
        dayWithMonth: String,
    ): GameDate {
        //TODO: Заменить на дату
        val date = LocalDate.of(2024, 10, day.toInt())
        return GameDate(
            localDate = date,
            day = day,
            month = month,
            dayOfTheWeek = DayOfTheWeekUtils.formatFullDayName(dayOfTheWeek),
            time = time.trim(),
            dayWithMonth = dayWithMonth,
        )
    }

}