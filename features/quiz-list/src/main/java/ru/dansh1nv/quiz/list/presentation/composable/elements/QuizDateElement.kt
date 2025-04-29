package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.quiz.list.models.item.GameDateUI

@Composable
internal fun QuizDateElement(gameDate: GameDateUI, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        TextCell(
            text = gameDate.dateText,
            style = QuizHubTheme.typography.labelLarge
        )
        TextCell(
            text = gameDate.timeWithDay,
            style = QuizHubTheme.typography.labelLarge,
        )
    }
}