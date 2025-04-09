package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.quiz.list.models.item.QuizUI

@Composable
internal fun QuizTitleElement(quizGame: QuizUI, modifier: Modifier) {
    Column(modifier = modifier) {
        Row {
            TextCell(
                text = quizGame.theme,
                style = QuizHubTheme.typography.titleLarge
            )
        }
        Row {
            TextCell(
                text = quizGame.description,
                style = QuizHubTheme.typography.titleMedium
            )
        }
    }
}