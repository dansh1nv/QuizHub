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
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.R
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
            val uniqueOrganizations = events.map { it.organization }.distinct()
            val groupCount = uniqueOrganizations.size

            if (groupCount == 0) return@Canvas

            if (groupCount == 1) {
                val color = when (uniqueOrganizations[0]) {
                    Organization.QUIZ_PLEASE -> orange
                    Organization.SQUIZ -> blue
                    Organization.SHAKER_QUIZ -> purple
                }

                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                return@Canvas
            }

            val totalAngle = 360f - (gapAngle * groupCount)
            val sweepAngle = totalAngle / groupCount
            var startAngle = -90f

            uniqueOrganizations.forEach { organization ->
                val color = when (organization) {
                    Organization.QUIZ_PLEASE -> orange
                    Organization.SQUIZ -> blue
                    Organization.SHAKER_QUIZ -> purple
                }

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Butt
                    )
                )

                startAngle += sweepAngle + gapAngle
            }
        }
    }
}