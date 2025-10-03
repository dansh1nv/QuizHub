package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.IconCell
import ru.quizHub.designsystem.theme.elements.TextCell
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.models.item.QuizUI

@Composable
internal fun QuizPriceElement(quizGame: QuizUI, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconCell(iconRes = UIDrawable.ic_price_white)
        Column {
            TextCell(text = quizGame.formatPrice)
            TextCell(text = quizGame.priceAdditionalText)
        }
    }
}