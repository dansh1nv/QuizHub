package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.elements.IconCell
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.QuizUI

@Composable
internal fun QuizPriceElement(quizGame: QuizUI, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconCell(iconRes = R.drawable.ic_price_white)
        Column {
            TextCell(text = quizGame.formatPrice)
            TextCell(text = quizGame.priceAdditionalText)
        }
    }
}