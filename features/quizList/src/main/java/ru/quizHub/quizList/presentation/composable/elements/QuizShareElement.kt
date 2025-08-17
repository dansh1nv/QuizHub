package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.R

@Composable
internal fun QuizShareElement(
    quizId: String,
    onShareClicked: (String) -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            onShareClicked(quizId)
        },
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(R.string.share_quiz),
            tint = QuizHubTheme.colorScheme.onSurfaceVariant
        )
    }
}