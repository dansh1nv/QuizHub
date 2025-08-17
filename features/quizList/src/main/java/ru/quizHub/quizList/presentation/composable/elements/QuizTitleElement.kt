package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.elements.TextCell
import ru.quizHub.quizList.models.item.QuizUI

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