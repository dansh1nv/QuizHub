package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.presentation.composable.TextCell

@Composable
internal fun QuizPriceElement(quizGame: QuizUI, modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        Column {
            Icon(
                painter = painterResource(id = R.drawable.ic_price_white),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            TextCell(
                text = quizGame.formatPrice
            )

            TextCell(
                text = quizGame.priceAdditionalText
            )
        }
    }
}