package ru.quizHub.designsystem.theme.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.QuizHubRadioButton
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun <Item> SingleSelectableListItem(
    text: String,
    item: Item,
    localState: MutableState<Item>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val selectItem = {
        localState.value = item
        onClick.invoke()
    }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { selectItem() },
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

        QuizHubRadioButton(
            isSelected = localState.value == item,
            onClick = { selectItem() },
            interactionSource = interactionSource
        )
    }
}