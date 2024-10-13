package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz.list.presentation.composable.TextCell

@Composable
fun QuizTitleElement(quizGame: QuizUI, modifier: Modifier) {
    Column(modifier = modifier) {
        Row {
            TextCell(
                text = quizGame.theme,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row {
            TextCell(
                text = quizGame.description,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}