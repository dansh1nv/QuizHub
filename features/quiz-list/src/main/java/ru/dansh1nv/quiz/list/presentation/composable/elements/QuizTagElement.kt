package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.designsystem.theme.tag.toBackgroundColor
import ru.dansh1nv.designsystem.theme.tag.toTextColor
import ru.dansh1nv.quiz.list.models.TagModel

@Composable
internal fun QuizTagElement(
    model: TagModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = model.tag.toBackgroundColor(),
                shape = QuizHubTheme.shapes.small,
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        TextCell(
            text = stringResource(id = model.title),
            color = model.tag.toTextColor(),
        )
    }
}