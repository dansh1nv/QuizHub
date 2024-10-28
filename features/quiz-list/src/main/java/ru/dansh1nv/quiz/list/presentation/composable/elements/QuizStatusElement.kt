package ru.dansh1nv.quiz.list.presentation.composable.elements

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
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.designsystem.theme.status.toBackgroundColor
import ru.dansh1nv.designsystem.theme.status.toTextColor
import ru.dansh1nv.quiz.list.models.item.StatusUI

@Composable
internal fun QuizStatusElement(
    status: StatusUI,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 4.dp)
            .background(
                color = status.tag.toBackgroundColor(),
                shape = QuizHubTheme.shapes.medium,
            ),
    ) {
        TextCell(
            text = stringResource(id = status.titleRes),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = status.tag.toTextColor(),
        )
    }
}