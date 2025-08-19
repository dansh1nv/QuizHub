package ru.quizHub.designsystem.theme.elements

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun QuizHubCheckBox(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.colors().copy(
        checkedCheckmarkColor = QuizHubTheme.colorScheme.surfaceTint,
        uncheckedCheckmarkColor = Color.Transparent,
        checkedBoxColor = QuizHubTheme.colorScheme.surfaceContainer,
        uncheckedBoxColor = Color.Transparent,
    ),
    onClick: (Boolean) -> Unit = {},
) {
    Checkbox(
        modifier = modifier,
        checked = isSelected,
        onCheckedChange = onClick,
        colors = colors,
    )
}