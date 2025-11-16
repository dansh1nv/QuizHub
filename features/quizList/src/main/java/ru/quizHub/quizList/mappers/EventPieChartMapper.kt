package ru.quizHub.quizList.mappers

import androidx.compose.ui.graphics.Color
import ru.quizHub.designsystem.theme.uiKit.customColor
import ru.quizHub.quizList.models.item.CalendarEventUI
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI

object EventPieChartMapper {
    private const val GAP_ANGLE = 4f
    private const val START_ANGLE = -90f
    private const val MAX_ANGLE = 360f

    fun mapToCalendarEventsUI(
        events: List<QuizUI>,
    ): List<CalendarEventUI> {
        return events
            .asSequence()
            .filter { it.formattedDate != null }
            .groupBy { it.formattedDate!!.date?.date }
            .flatMap { (_, dateEvents) ->
                val uniqueOrganizations = dateEvents.map { it.organization }.distinct().sorted()
                val groupCount = uniqueOrganizations.size

                uniqueOrganizations.mapIndexed { index, org ->
                    CalendarEventUI(
                        color = getColor(org),
                        startAngle = calculateStartAngle(index, groupCount),
                        sweepAngle = calculateSweepAngle(groupCount),
                        formattedDate = dateEvents.first { it.organization == org }.formattedDate
                    )
                }
            }
    }

    private fun calculateStartAngle(index: Int, groupCount: Int): Float {
        val sweep = calculateSweepAngle(groupCount)
        return START_ANGLE + (sweep + GAP_ANGLE) * index
    }

    private fun calculateSweepAngle(groupCount: Int): Float {
        return ((MAX_ANGLE - (GAP_ANGLE * groupCount)) / groupCount)
            .takeIf { groupCount > 1 } ?: MAX_ANGLE
    }

    private fun getColor(org: Organization): Color {
        val customColors = customColor()
        return when (org) {
            Organization.QUIZ_PLEASE -> customColors.red
            Organization.SQUIZ -> customColors.blue
            Organization.SHAKER_QUIZ -> customColors.purple
            Organization.RUDA_GAMES -> customColors.orange
        }
    }
}