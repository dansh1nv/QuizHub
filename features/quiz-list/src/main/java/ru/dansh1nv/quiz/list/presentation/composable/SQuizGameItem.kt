package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.models.SQuizUI
import ru.dansh1nv.quiz.list.presentation.TextCell

@Composable
fun SQuizGameItem(quizGame: SQuizUI) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            )
            .background(
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                color = MaterialTheme.colorScheme.background
            )
            .padding(12.dp)
    ) {
        val modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp)
        AsyncImage(
            model = quizGame.image,
            contentDescription = null,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        )
        QuizDateElement(quizGame.gameDate, modifier)
        quizGame.takeIf { it.additionDescription.isNotBlank() }?.let {
            QuizReplyElement(quizGame.additionDescription, modifier)
        }
        QuizTitleElement(quizGame, modifier)
        QuizLocationElement(quizGame, modifier)
        QuizPriceElement(quizGame, modifier)
    }
}

@Composable
fun QuizDateElement(gameDate: GameDateUI, modifier: Modifier) {
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

@Composable
fun QuizReplyElement(additionDescription: String, modifier: Modifier) {
    Column(modifier = modifier) {
        TextCell(
            text = additionDescription,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun QuizTitleElement(quizGame: SQuizUI, modifier: Modifier) {
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

@Composable
fun QuizLocationElement(quizGame: SQuizUI, modifier: Modifier) {
    Row(modifier = modifier) {
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_map_white),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            TextCell(text = quizGame.location)
            TextCell(text = quizGame.address)
        }
    }
}

@Composable
fun QuizPriceElement(quizGame: SQuizUI, modifier: Modifier) {
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
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            TextCell(
                text = "${quizGame.price} ₽"
            )

            TextCell(
                text = stringResource(id = R.string.quiz_item_price_for_people)
            )
        }
    }
}