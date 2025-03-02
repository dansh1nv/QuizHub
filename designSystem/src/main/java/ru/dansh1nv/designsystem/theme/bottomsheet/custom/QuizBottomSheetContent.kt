package ru.dansh1nv.designsystem.theme.bottomsheet.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel
import ru.dansh1nv.designsystem.theme.bottomsheet.toolbar.QuizModalBottomSheetToolbar
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@Composable
internal fun QuizBottomSheetContent(
    modifier: Modifier = Modifier,
    model: QuizBottomSheetModel,
    needBottomPadding: Boolean = false,
    content: @Composable (ColumnScope.() -> Unit)?,
) {
    val bottomPadding = if (needBottomPadding) 12.dp else 0.dp
    Column(modifier) {
        model.toolbar?.let {
            QuizModalBottomSheetToolbar(it)
            HorizontalDivider(
                modifier = Modifier.padding(bottom = bottomPadding),
                color = QuizHubTheme.colorScheme.onSecondaryContainer
            )
        }
        content?.invoke(this)
    }
}
