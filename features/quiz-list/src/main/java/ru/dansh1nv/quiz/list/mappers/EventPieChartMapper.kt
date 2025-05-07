package ru.dansh1nv.quiz.list.mappers

import androidx.compose.ui.graphics.Color
import ru.dansh1nv.quiz.list.models.item.CalendarEventUI
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI

object EventPieChartMapper {
    fun mapToCalendarEvents(
        events: List<QuizUI>,
        colors: Map<Organization, Color>,
        gapAngle: Float
    ): List<CalendarEventUI> {
        val uniqueOrganization = events.map { it.organization }.distinct()
        val groupCount = uniqueOrganization.size
        val totalAngle = (360 - (gapAngle * groupCount)).takeIf { groupCount > 1 } ?: 360f
        val sweepAngle = totalAngle / groupCount
        var startAngle = -90f

        return uniqueOrganization.map { organization ->
            CalendarEventUI(
                color = colors[organization] ?: error("Color not defined for $organization"),
                startAngle = startAngle.also {
                    startAngle += sweepAngle + gapAngle
                },
                sweepAngel = sweepAngle
            )
        }
    }
}