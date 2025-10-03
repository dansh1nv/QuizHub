package ru.quizHub.quizList.presentation.composable.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.ScreenEvent
import ru.quizHub.quizList.presentation.composable.elements.QuizDateElement
import ru.quizHub.quizList.presentation.composable.elements.QuizDifficultElement
import ru.quizHub.quizList.presentation.composable.elements.QuizLocationElement
import ru.quizHub.quizList.presentation.composable.elements.QuizPriceElement
import ru.quizHub.quizList.presentation.composable.elements.QuizReplyElement
import ru.quizHub.quizList.presentation.composable.elements.QuizShareElement
import ru.quizHub.quizList.presentation.composable.elements.QuizStatusElement
import ru.quizHub.quizList.presentation.composable.elements.QuizTagElement
import ru.quizHub.quizList.presentation.composable.elements.QuizTeamElement
import ru.quizHub.quizList.presentation.composable.elements.QuizTitleElement

@Composable
internal fun QuizCard(
    quizGame: QuizUI,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 4.dp,
                shape = QuizHubTheme.shapes.shape8dp,
            )
            .background(
                shape = QuizHubTheme.shapes.shape8dp,
                color = QuizHubTheme.colorScheme.surfaceContainer
            )
            .padding(12.dp)
            .clickable { onUIEvent(ScreenEvent.OnCardItemClicked("test_id")) }
    ) {
        val modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp)
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(quizGame.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(QuizHubTheme.shapes.shape16dp)
            )
            QuizTagElement(
                model = quizGame.tag,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .border(
                        width = 2.dp,
                        color = QuizHubTheme.colorScheme.outlineVariant,
                        shape = QuizHubTheme.shapes.shape8dp
                    )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            quizGame.formattedDate?.let { QuizDateElement(quizGame.formattedDate, modifier) }
            QuizShareElement(
                quiz = quizGame,
                onShareClicked = { quiz ->
                    onUIEvent(ScreenEvent.OnShareEventClick(quiz))
                },
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }
        quizGame.takeIf { it.additionDescription.isNotBlank() }?.let {
            QuizReplyElement(quizGame.additionDescription, modifier)
        }
        QuizTitleElement(quizGame, modifier)
        if (quizGame.difficulty.isNotBlank()) {
            QuizDifficultElement(quizGame.difficulty, modifier)
        }
        quizGame.teamSize?.teamSizeText?.let {
            QuizTeamElement(teamSizeUI = quizGame.teamSize, modifier = modifier)
        }
        quizGame.location?.let {
            QuizLocationElement(
                quizGame.location,
                modifier,
                onLocationClick = { onUIEvent(ScreenEvent.OnShowLocationEventClick(quizGame)) }
            )
        }
        QuizPriceElement(quizGame, modifier)
        quizGame.status?.let { QuizStatusElement(status = quizGame.status) }
    }
}