package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.presentation.composable.TextCell

@Composable
internal fun QuizDateElement(gameDate: GameDateUI, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        TextCell(
            text = gameDate.dateText,
            style = MaterialTheme.typography.labelLarge
        )
        TextCell(
            text = gameDate.timeWithDay,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}