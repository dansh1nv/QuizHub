package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.elements.TextCell
import ru.quizHub.designsystem.theme.status.toBackgroundColor
import ru.quizHub.designsystem.theme.status.toTextColor
import ru.quizHub.quizList.models.item.StatusUI

@Composable
internal fun QuizStatusElement(
    status: StatusUI,
) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = status.tag.toBackgroundColor(),
                shape = QuizHubTheme.shapes.shape8dp,
            )
            .padding(all = 8.dp),
    ) {
        TextCell(
            text = stringResource(id = status.titleRes),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = status.tag.toTextColor(),
        )
    }
}