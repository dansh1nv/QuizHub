package ru.quizHub.quizList.models.bottomsheet

import ru.quizHub.designsystem.theme.bottomsheet.model.QuizBottomSheetModel
import ru.quizHub.quizList.models.item.CalendarEventUI

internal sealed class BottomSheetModels :
    QuizBottomSheetModel(isDragHandleVisible = false, isScrimVisible = true) {

    data class FilterBottomSheetModel(
        override val toolbar: Toolbar
    ) : BottomSheetModels()

    data class SortingBottomSheetModel(
        override val toolbar: Toolbar
    ) : BottomSheetModels()

    data class CalendarBottomSheetModel(
        override val toolbar: Toolbar,
        val events: List<CalendarEventUI>
    ) : BottomSheetModels()

    data class CityBottomSheetModel(
        override val toolbar: Toolbar,
    ): BottomSheetModels()
}