package ru.dansh1nv.quiz.data.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import ru.dansh1nv.quiz.data.Constants
import ru.dansh1nv.quiz.data.utils.DayOfTheWeekUtils
import ru.dansh1nv.quiz.data.utils.MonthConverter
import ru.dansh1nv.quiz_list_domain.models.GameDate
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quizapi.model.squiz.Characteristic
import ru.dansh1nv.quizapi.model.squiz.SquizDTO

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
            ),
            type = gameType,
            format = gameFormat,
            theme = theme,
            packageNumber = packageNumber,
            description = description,
            additionDescription = additionDescription,
            image = img,
            place = address.getOrNull(0) ?: "Онлайн",
            address = address.getOrNull(1).orEmpty(),
            price = price.toString(),
            status = status,
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
    ): GameDate {
        val timeArray = time.trim().split(":", limit = 2)
        //TODO: Подумать откуда взять бы год
        val localDate = LocalDate(
            year = 2024,
            monthNumber = MonthConverter.getMonthByName(month),
            dayOfMonth = day.toInt()
        )
        val localTime = LocalTime(
            hour = timeArray[0].toInt(),
            minute = timeArray[1].toInt(),
        )
        return GameDate(
            dateTime = LocalDateTime(
                date = localDate,
                time = localTime,
            ),
            day = day,
            month = month,
            time = time.trim(),
        )
    }

}