package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.coroutines.launch
import ru.dansh1nv.core.presentation.calendar.ContinuousSelectionHelper.getSelection
import ru.dansh1nv.core.presentation.calendar.DateSelection
import ru.dansh1nv.core.presentation.calendar.rememberFirstMostVisibleMonth
import ru.dansh1nv.designsystem.R
import ru.dansh1nv.designsystem.theme.elements.QuizHubButton
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.item.CalendarEventUI
import ru.dansh1nv.quiz.list.presentation.BottomSheetEvent
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.composable.calendar.Day
import ru.dansh1nv.quiz.list.presentation.composable.calendar.MonthHeader
import ru.dansh1nv.quiz.list.presentation.composable.calendar.SimpleCalendarTitle

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
    ) {
        val calendarState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(calendarState, viewportPercent = 90f)
        SimpleCalendarTitle(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    calendarState.animateScrollToMonth(
                        calendarState.firstVisibleMonth.yearMonth.minusMonths(1)
                    )
                }
            },
            goToNext = {
                coroutineScope.launch {
                    calendarState.animateScrollToMonth(
                        calendarState.firstVisibleMonth.yearMonth.plusMonths(1)
                    )
                }
            },
        )

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
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(QuizHubTheme.colorScheme.surfaceContainer)
                .padding(16.dp)
        ) {
            QuizHubButton(
                title = stringResource(R.string.apply_button_text),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = selection.startDate != null,
                onClick = {
                    onUIEvent(BottomSheetEvent.OnCalendarDaySelected(selection))
                }
            )
        }
    }
}
