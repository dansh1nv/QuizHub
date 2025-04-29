package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.quiz.list.R

@Composable
internal fun QuizDifficultElement(difficult: String, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        TextCell(
            text = stringResource(id = R.string.quiz_item_difficult_title),
            style = QuizHubTheme.typography.labelLarge,
        )
        TextCell(
            text = difficult,
            style = QuizHubTheme.typography.labelLarge,
        )
    }
}