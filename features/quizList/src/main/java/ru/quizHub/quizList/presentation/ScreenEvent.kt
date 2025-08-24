package ru.quizHub.quizList.presentation

import ru.quizHub.core.presentation.UIEvent
import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.quizList.models.CityModel
import ru.quizHub.quizList.models.filters.Filters
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.models.sorting.Sort

internal sealed interface QuizListEvent : UIEvent

internal sealed interface ScreenEvent : QuizListEvent {
    data object OnFiltersButtonClick : ScreenEvent
    data object OnSortButtonClick : ScreenEvent
    data object OnLocationClick : ScreenEvent
    data class OnTabClick(val index: Int) : ScreenEvent
    data object OnRefresh : ScreenEvent
    data object OnCalendarClick : ScreenEvent
    data class OnCardItemClicked(val id: String) : ScreenEvent
    data class OnShareEventClick(val quiz: QuizUI) : ScreenEvent
    data object ResetFilters : ScreenEvent
    data class OnSearch(val query: String) : ScreenEvent
    data class OnCityClick(val city: CityModel) : ScreenEvent
    data class OnShowLocationEventClick(val quiz: QuizUI) : ScreenEvent
}

internal sealed interface BottomSheetEvent : QuizListEvent {
    data class OnFilterClick(val filters: Filters) : BottomSheetEvent
    data class OnSortClick(val sort: Sort) : BottomSheetEvent
    data class OnCalendarDaySelected(val days: DateSelection) : BottomSheetEvent
}
