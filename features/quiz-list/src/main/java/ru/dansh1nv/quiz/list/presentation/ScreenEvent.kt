package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.UIEvent
import ru.dansh1nv.quiz.list.models.Organization

internal sealed interface ScreenEvent: UIEvent {
    data class OnFiltersClick(val isShow: Boolean) : ScreenEvent
    data class OnSortClick(val isShow: Boolean) : ScreenEvent
    data object BottomSheetDismiss : ScreenEvent
    data object OnLocationClick : ScreenEvent
    data class OnTabClick(val index: Int) : ScreenEvent
    data class FilterClick(val organization: Organization?): ScreenEvent
}