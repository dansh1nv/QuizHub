package ru.quizHub.quizList.presentation.composable.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.datetime.DayOfWeek
import ru.quizHub.core.presentation.calendar.displayText
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.color.CustomColorModel
import ru.quizHub.designsystem.theme.utils.color.toTextColor

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.displayText(),
                style = QuizHubTheme.typography.titleMedium,
                color = CustomColorModel.Surface.toTextColor()
            )
        }
    }
}