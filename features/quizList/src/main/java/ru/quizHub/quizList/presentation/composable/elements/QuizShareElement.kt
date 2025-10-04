package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.item.QuizUI

@Composable
internal fun QuizShareElement(
    quiz: QuizUI,
    onShareClicked: (QuizUI) -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            onShareClicked(quiz)
        },
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            painter = painterResource(UIDrawable.ic_remix_share_fill),
            contentDescription = stringResource(R.string.share_quiz),
            tint = QuizHubTheme.colorScheme.onSurfaceVariant
        )
    }
}