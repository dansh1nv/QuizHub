package ru.dansh1nv.quiz.list.presentation.composable.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.color.CustomColorModel
import ru.dansh1nv.designsystem.theme.utils.color.toTextColor
import ru.dansh1nv.quiz.list.models.item.QuizUI

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    events: List<QuizUI>,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (events.isNotEmpty()) {
            EventPieChart(
                events = events,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = CustomColorModel.Surface.toTextColor(),
            style = QuizHubTheme.typography.labelLarge
        )
    }
}