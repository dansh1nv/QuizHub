package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.quizHub.core.presentation.calendar.clickable
import ru.quizHub.designsystem.theme.elements.IconCell
import ru.quizHub.designsystem.theme.elements.TextCell
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.models.item.LocationUI

@Composable
internal fun QuizLocationElement(
    model: LocationUI,
    modifier: Modifier,
    onLocationClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    if (!model.isOnline) {
                        onLocationClick()
                    }
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCell(iconRes = UIDrawable.ic_map_white)
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                TextCell(text = model.place)
                if (model.address.isNotEmpty()) {
                    TextCell(text = model.address)
                }
            }
        }
        if (!model.isOnline) {
            Icon(
                painter = painterResource(UIDrawable.ic_arrow_right_s),
                contentDescription = null,
                tint = QuizHubTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}