package ru.quizHub.quizList.presentation

import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.quizList.models.item.Organization

internal data class FiltersState(
    val organizations: List<Organization> = emptyList(),
    val dateSelection: DateSelection? = null,
    val isApplied: Boolean = false,
)