package ru.dansh1nv.quiz.list.presentation.composable.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.YearMonth
import kotlinx.datetime.DayOfWeek
import ru.dansh1nv.core.presentation.calendar.displayText
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.color.CustomColorModel
import ru.dansh1nv.designsystem.theme.utils.color.toTextColor

@Composable
fun MonthHeader(
    daysOfWeek: List<DayOfWeek>,
    month: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit
) {
    Column {
        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = month,
            goToPrevious = goToPrevious,
            goToNext = goToNext
        )
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
}