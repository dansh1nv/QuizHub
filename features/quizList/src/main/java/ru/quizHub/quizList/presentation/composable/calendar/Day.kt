package ru.quizHub.quizList.presentation.composable.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.core.presentation.calendar.backgroundHighlight
import ru.quizHub.core.presentation.calendar.clickable
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.item.CalendarEventUI

@Composable
fun Day(
    day: CalendarDay,
    selection: DateSelection,
    events: List<CalendarEventUI>,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = QuizHubTheme.colorScheme.onSurface
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                showRipple = false,
                onClick = { onClick(day) }
            )
            .backgroundHighlight(
                day = day,
                selection = selection,
                selectionColor = QuizHubTheme.colorScheme.surfaceContainerHighest,
                continuousSelectionColor = QuizHubTheme.colorScheme.surfaceContainerHigh
            ) { textColor = it },
        contentAlignment = Alignment.Center
    ) {
        if (events.isNotEmpty()) {
            EventPieChart(
                events = events,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = QuizHubTheme.typography.labelLarge
        )
    }
}