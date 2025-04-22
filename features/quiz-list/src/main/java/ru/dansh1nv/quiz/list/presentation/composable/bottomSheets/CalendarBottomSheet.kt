package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dongchyeon.calendar.ui.Calendar
import com.dongchyeon.calendar.util.now
import kotlinx.datetime.LocalDate
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel
import ru.dansh1nv.quiz.list.presentation.extensions.getCalendarEvents

@Composable
internal fun CalendarBottomSheet(
    viewModel: QuizListViewModel
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val events by remember { derivedStateOf { viewModel.getCalendarEvents() } }
    Calendar(
        selectedDate = selectedDate,
        events = events
    ) {
        selectedDate = it
    }
}