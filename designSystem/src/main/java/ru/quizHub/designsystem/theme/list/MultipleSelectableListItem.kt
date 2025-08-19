package ru.quizHub.designsystem.theme.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.QuizHubCheckBox
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun <Item> MultipleSelectableListItem(
    text: String,
    isSelected: Boolean,
    item: Item,
    localState: MutableState<Item>,
    onClick: (Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                localState.value = item
                onClick.invoke(!isSelected)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            style = QuizHubTheme.typography.bodyLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
        )

        QuizHubCheckBox(
            isSelected = localState.value == item,
            onClick = onClick,
        )
    }
}