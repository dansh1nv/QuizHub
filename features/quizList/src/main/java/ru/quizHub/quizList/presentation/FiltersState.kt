package ru.quizHub.quizList.presentation

import com.kizitonwose.calendar.core.CalendarDay
import ru.quizHub.quizList.models.filters.Filters

internal data class FiltersState(
    val filters: Filters? = null,
    val filterByDay: CalendarDay? = null,
    val isApplied: Boolean = false,
)