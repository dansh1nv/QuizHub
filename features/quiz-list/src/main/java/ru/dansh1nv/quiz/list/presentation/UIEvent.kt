package ru.dansh1nv.quiz.list.presentation

internal sealed interface UIEvent {
    data object OnFiltersClick : UIEvent
    data object OnSortClick : UIEvent
    data object OnLocationClick : UIEvent
    data class OnTabClick(val id: Int) : UIEvent
}