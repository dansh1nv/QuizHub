package ru.dansh1nv.quiz.list.presentation.composable.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizDateElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizDifficultElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizLocationElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizPriceElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizReplyElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizStatusElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizTagElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizTeamElement
import ru.dansh1nv.quiz.list.presentation.composable.elements.QuizTitleElement

@Composable
internal fun QuizCard(quizGame: QuizUI) {
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
                .clip(QuizHubTheme.shapes.shape16dp),
        )
        QuizTagElement(
            model = quizGame.tag,
            modifier = modifier,
        )
        Spacer(modifier = Modifier.size(8.dp))
        quizGame.formattedDate?.let { QuizDateElement(quizGame.formattedDate, modifier) }
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
        quizGame.location?.let { QuizLocationElement(quizGame.location, modifier) }
        QuizPriceElement(quizGame, modifier)
        quizGame.status?.let { QuizStatusElement(status = quizGame.status) }
    }
}