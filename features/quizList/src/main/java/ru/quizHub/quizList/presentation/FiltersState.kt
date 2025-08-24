package ru.quizHub.quizList.presentation

import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.quizList.models.filters.Filters

internal data class FiltersState(
    val filters: Filters? = null,
    val dateSelection: DateSelection? = null,
    val isApplied: Boolean = false,
)