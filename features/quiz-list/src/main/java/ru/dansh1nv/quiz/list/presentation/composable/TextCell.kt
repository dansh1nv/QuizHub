package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
internal fun TextCell(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = style,
    )
}