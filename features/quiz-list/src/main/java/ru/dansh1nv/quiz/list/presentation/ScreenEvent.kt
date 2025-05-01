package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.UIEvent
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.sorting.Sort

internal sealed interface QuizListEvent : UIEvent

internal sealed interface ScreenEvent : QuizListEvent {
    data object OnFiltersButtonClick : ScreenEvent
    data object OnSortButtonClick : ScreenEvent
    data object BottomSheetDismiss : ScreenEvent
    data object OnLocationClick : ScreenEvent
    data class OnTabClick(val index: Int) : ScreenEvent
    data object OnRefresh : ScreenEvent
    data object OnCalendarClick : ScreenEvent
    data class OnCardItemClicked(val id: String) : ScreenEvent
}

internal sealed interface BottomSheetEvent : QuizListEvent {
    data class OnFilterClick(val organization: Organization?) : BottomSheetEvent
    data class OnSortClick(val sort: Sort) : BottomSheetEvent
}
