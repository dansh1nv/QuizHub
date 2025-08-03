package ru.dansh1nv.quiz.list.presentation

import com.kizitonwose.calendar.core.CalendarDay
import ru.dansh1nv.quiz.list.models.filters.Filters

internal data class FiltersState(
    val filters: Filters? = null,
    val selectedDays: List<CalendarDay> = emptyList(),
    val isApplied: Boolean = false,
)