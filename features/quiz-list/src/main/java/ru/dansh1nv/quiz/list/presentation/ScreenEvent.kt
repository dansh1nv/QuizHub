package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.UIEvent

internal sealed interface ScreenEvent: UIEvent {
    data object OnFiltersClick : ScreenEvent
    data object OnSortClick : ScreenEvent
    data object OnLocationClick : ScreenEvent
    data class OnTabClick(val index: Int) : ScreenEvent
    data object OnRefresh : ScreenEvent
}