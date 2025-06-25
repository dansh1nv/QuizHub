package ru.dansh1nv.designsystem.theme.elements

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun QuizHubRadioButton(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors().copy(
        selectedColor = QuizHubTheme.colorScheme.surfaceTint,
        unselectedColor = QuizHubTheme.colorScheme.surfaceTint,
        disabledSelectedColor = QuizHubTheme.colorScheme.surfaceTint.copy(alpha = 0.1f),
        disabledUnselectedColor = QuizHubTheme.colorScheme.surfaceTint.copy(alpha = 0.1f),
    ),
    onClick: () -> Unit = {},
) {
    RadioButton(
        selected = isSelected,
        modifier = modifier,
        onClick = onClick,
        colors = colors,
    )
}