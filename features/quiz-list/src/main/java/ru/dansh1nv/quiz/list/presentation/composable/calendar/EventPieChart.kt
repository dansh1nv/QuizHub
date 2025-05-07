package ru.dansh1nv.quiz.list.presentation.composable.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.mappers.EventPieChartMapper
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI

@Composable
fun EventPieChart(
    events: List<QuizUI>,
    modifier: Modifier,
    strokeWidth: Dp = 8.dp,
    gapAngle: Float = 4f
) {
    val orange = QuizHubTheme.customColor.orange
    val blue = QuizHubTheme.customColor.blue
    val purple = QuizHubTheme.customColor.purple
    val colors = mapOf(
        Organization.QUIZ_PLEASE to orange,
        Organization.SQUIZ to blue,
        Organization.SHAKER_QUIZ to purple
    )
    val calendarEvents = remember(events) {
        EventPieChartMapper.mapToCalendarEvents(
            events = events,
            colors = colors,
            gapAngle = gapAngle
        )
    }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_calendar_ring),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            calendarEvents.forEach { calendarEvent ->
                drawArc(
                    color = calendarEvent.color,
                    startAngle = calendarEvent.startAngle,
                    sweepAngle = calendarEvent.sweepAngel,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Butt
                    )
                )
            }
        }
    }
}