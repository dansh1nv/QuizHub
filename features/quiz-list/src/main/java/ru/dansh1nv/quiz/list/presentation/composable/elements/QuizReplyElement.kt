package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dansh1nv.quiz.list.presentation.composable.TextCell

@Composable
internal fun QuizReplyElement(additionDescription: String, modifier: Modifier) {
    Column(modifier = modifier) {
        TextCell(
            text = additionDescription,
            style = MaterialTheme.typography.labelMedium
        )
    }
}