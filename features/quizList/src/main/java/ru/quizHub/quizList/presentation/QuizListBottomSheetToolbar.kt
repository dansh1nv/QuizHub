package ru.quizHub.quizList.presentation

import ru.quizHub.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar
import ru.quizHub.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar.IconModel
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable

/**
 * Одинаковый тулбар с кнопкой закрытия для всех bottom sheet списка квизов.
 */
internal object QuizListBottomSheetToolbar {

    fun withCloseButton(
        title: String,
        onCloseClick: () -> Unit,
    ): Toolbar = Toolbar(
        title = title,
        trailIcon = IconModel(
            iconRes = UIDrawable.ic_remix_close,
            onClick = onCloseClick,
        ),
    )
}
