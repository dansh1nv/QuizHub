package ru.quizHub.quizList.presentation.composable.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.image.QuizImage
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.ScreenEvent
import ru.quizHub.quizList.presentation.composable.elements.QuizAddToCalendarElement
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
    isCardDetailsEnabled: Boolean,
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
            .then(
                if (isCardDetailsEnabled) {
                    Modifier.clickable {
                        onUIEvent(ScreenEvent.OnCardItemClicked(quizGame.id))
                    }
                } else {
                    Modifier
                }
            )
    ) {
        val modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp)
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            QuizImage(
                modifier = Modifier.fillMaxWidth(),
                url = quizGame.image,
                contentScale = ContentScale.FillBounds,
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
            quizGame.formattedDate?.let {
                QuizDateElement(
                    gameDate = quizGame.formattedDate,
                    modifier = modifier
                )
            }
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                QuizAddToCalendarElement(
                    quiz = quizGame,
                    onAddToCalendarClicked = { quiz ->
                        onUIEvent(ScreenEvent.OnAddToCalendarClick(quiz))
                    },
                )
                QuizShareElement(
                    quiz = quizGame,
                    onShareClicked = { quiz ->
                        onUIEvent(ScreenEvent.OnShareEventClick(quiz))
                    },
                    modifier = Modifier,
                )
            }
        }
        quizGame.takeIf { it.additionDescription.isNotBlank() }?.let {
            QuizReplyElement(
                additionDescription = quizGame.additionDescription,
                modifier = modifier,
            )
        }
        QuizTitleElement(quizGame, modifier)
        if (quizGame.difficulty.isNotBlank()) {
            QuizDifficultElement(difficult = quizGame.difficulty, modifier = modifier)
        }
        quizGame.teamSize?.teamSizeText?.let {
            QuizTeamElement(teamSizeUI = quizGame.teamSize, modifier = modifier)
        }
        quizGame.location?.let {
            QuizLocationElement(
                model = quizGame.location,
                modifier = modifier,
                onLocationClick = { onUIEvent(ScreenEvent.OnShowLocationEventClick(quizGame)) }
            )
        }
        QuizPriceElement(quizGame, modifier)
        quizGame.status?.let { QuizStatusElement(status = quizGame.status) }
    }
}