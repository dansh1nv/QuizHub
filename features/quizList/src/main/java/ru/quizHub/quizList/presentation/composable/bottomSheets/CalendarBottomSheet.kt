package ru.quizHub.quizList.presentation.composable.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.coroutines.launch
import kotlinx.datetime.YearMonth
import ru.quizHub.core.presentation.calendar.ContinuousSelectionHelper.getSelection
import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.designsystem.R
import ru.quizHub.designsystem.theme.elements.QuizHubButton
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.item.CalendarEventUI
import ru.quizHub.quizList.presentation.BottomSheetEvent
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.composable.calendar.Day
import ru.quizHub.quizList.presentation.composable.calendar.MonthHeader

@Composable
internal fun CalendarBottomSheet(
    adjacentMonths: Int = 12,
    events: List<CalendarEventUI>,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    var selection by remember { mutableStateOf(DateSelection()) }
    val daysOfWeek = remember { daysOfWeek() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizHubTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val calendarState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val coroutineScope = rememberCoroutineScope()

        HorizontalCalendar(
            state = calendarState,
            dayContent = { value ->
                val dayEvents = events.filter { quiz ->
                    quiz.formattedDate?.date?.date == value.date
                }
                Day(
                    day = value,
                    selection = selection,
                    events = dayEvents
                ) { day ->
                    if (day.position == DayPosition.MonthDate) {
                        selection = getSelection(
                            clickedDate = day.date,
                            dateSelection = selection
                        )
                    }
                }
            },
            monthHeader = { month ->
                MonthHeader(
                    daysOfWeek = daysOfWeek,
                    month = month.yearMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(
                                month.yearMonth.minusMonths(1)
                            )
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(
                                month.yearMonth.plusMonths(1)
                            )
                        }
                    }
                )
            },
        )

        QuizHubButton(
            title = stringResource(R.string.apply_button_text),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            isEnabled = selection.startDate != null,
            onClick = {
                onUIEvent(BottomSheetEvent.OnCalendarDaySelected(selection))
            }
        )
    }
}
