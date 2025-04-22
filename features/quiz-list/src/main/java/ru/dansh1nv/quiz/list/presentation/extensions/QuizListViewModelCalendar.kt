package ru.dansh1nv.quiz.list.presentation.extensions

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.dongchyeon.calendar.CalendarEvent
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel

internal fun QuizListViewModel.getCalendarEvents(): List<CalendarEvent> {
    return container.stateFlow.value.quizList.mapNotNull { quiz ->
        quiz.formattedDate?.date?.date?.let { date ->
            CalendarEvent(
                date = date,
                imgUrl = quiz.image,
                imgShape = RoundedCornerShape(8.dp)
            )
        }
    }
}
//TODO: добавить расширения для получения состояния даты (из вью модели) и её обновления