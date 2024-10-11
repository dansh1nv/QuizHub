package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.QuizPleaseUI

@Composable
internal fun QuizPleaseItem(quizGame: QuizPleaseUI) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 4.dp,
                shape = QuizHubTheme.shapes.small,
            )
            .background(
                shape = QuizHubTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .padding(12.dp)
    ) {
        val modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(quizGame.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                .clip(QuizHubTheme.shapes.large),
        )
        QuizDateElement(quizGame.gameDate, modifier)
        QuizTitleElement(quizGame, modifier)
        QuizLocationElement(quizGame, modifier)
        QuizPriceElement(quizGame, modifier)
    }
}

@Composable
fun QuizTitleElement(quizGame: QuizPleaseUI, modifier: Modifier) {
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
fun QuizLocationElement(quizGame: QuizPleaseUI, modifier: Modifier) {
    Row(modifier = modifier) {
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_map_white),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            TextCell(text = quizGame.place)
            TextCell(text = quizGame.address)
        }
    }
}

@Composable
fun QuizPriceElement(quizGame: QuizPleaseUI, modifier: Modifier) {
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
                text = stringResource(id = R.string.quiz_item_price_for_people)
            )
        }
    }
}