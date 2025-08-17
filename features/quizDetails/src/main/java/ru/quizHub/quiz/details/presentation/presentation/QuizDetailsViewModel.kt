package ru.quizHub.quiz.details.presentation.presentation

import androidx.compose.runtime.Immutable
import ru.quizHub.core.presentation.viewModel.BaseMviViewModel
import ru.quizHub.core.presentation.ScreenState

internal class QuizDetailsViewModel :
    BaseMviViewModel<QuizDetailsState, QuizDetailsSideEffect, ScreenEvent>(QuizDetailsState()) {

    override fun handleEvent(event: ScreenEvent) {

    }

}

@Immutable
internal data class QuizDetailsState(
    val title: String = "Заглушка"
) : ScreenState

