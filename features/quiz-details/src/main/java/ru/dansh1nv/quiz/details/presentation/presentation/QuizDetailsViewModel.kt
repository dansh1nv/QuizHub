package ru.dansh1nv.quiz.details.presentation.presentation

import androidx.compose.runtime.Immutable
import ru.dansh1nv.core.presentation.BaseMviViewModel
import ru.dansh1nv.core.presentation.ScreenState

internal class QuizDetailsViewModel :
    BaseMviViewModel<QuizDetailsState, QuizDetailsSideEffect, ScreenEvent>(QuizDetailsState()) {

    override fun handleEvent(event: ScreenEvent) {

    }

}

@Immutable
internal data class QuizDetailsState(
    val title: String = "Заглушка"
) : ScreenState

