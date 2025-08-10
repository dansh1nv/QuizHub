package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.calendar.DateSelection
import ru.dansh1nv.quiz.list.models.filters.Filters

internal data class FiltersState(
    val filters: Filters? = null,
    val dateSelection: DateSelection? = null,
    val isApplied: Boolean = false,
)