package ru.quizHub.quizList.presentation.composable.calendar

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
import ru.quizHub.core.presentation.calendar.clickable
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.item.CalendarEventUI

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    events: List<CalendarEventUI>,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape)
            .clickable { onClick.invoke() },
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
            color = QuizHubTheme.colorScheme.onSurface,
            style = QuizHubTheme.typography.labelLarge
        )
    }
}