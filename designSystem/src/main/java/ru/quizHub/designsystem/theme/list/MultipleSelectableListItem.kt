package ru.quizHub.designsystem.theme.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.QuizHubCheckBox
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun MultipleSelectableListItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {},
) {
    val (checkedState, onStateChange) = remember { mutableStateOf(isSelected) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = checkedState,
                onValueChange = {
                    onStateChange(!checkedState)
                    onClick(checkedState)
                },
                role = Role.Checkbox
            )
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = QuizHubTheme.typography.bodyLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier.wrapContentWidth(),
        )

        QuizHubCheckBox(isSelected = checkedState)
    }
}