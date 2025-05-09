package ru.dansh1nv.quiz.list.presentation.composable.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.CalendarEventUI

@Composable
fun EventPieChart(
    events: List<CalendarEventUI>,
    modifier: Modifier,
    strokeWidth: Dp = 8.dp
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_calendar_ring),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Canvas(modifier = Modifier.matchParentSize()) {
            events.forEach { event ->
                drawArc(
                    color = event.color,
                    startAngle = event.startAngle,
                    sweepAngle = event.sweepAngle,
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