package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.elements.TextCell

@Composable
internal fun QuizReplyElement(additionDescription: String, modifier: Modifier) {
    Column(modifier = modifier) {
        TextCell(
            text = additionDescription,
            style = QuizHubTheme.typography.labelMedium
        )
    }
}