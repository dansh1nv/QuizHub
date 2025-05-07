package ru.dansh1nv.quiz.list.models.bottomsheet

import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel
import ru.dansh1nv.quiz.list.models.item.QuizUI

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
        val events: List<QuizUI>
    ) : BottomSheetModels()
}