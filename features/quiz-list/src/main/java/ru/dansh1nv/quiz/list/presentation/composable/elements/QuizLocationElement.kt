package ru.dansh1nv.quiz.list.presentation.composable.elements

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
import ru.dansh1nv.core.presentation.calendar.clickable
import ru.dansh1nv.designsystem.theme.elements.IconCell
import ru.dansh1nv.designsystem.theme.elements.TextCell
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.LocationUI

@Composable
internal fun QuizLocationElement(
    model: LocationUI,
    modifier: Modifier,
    onLocationClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onLocationClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCell(iconRes = R.drawable.ic_map_white)
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                TextCell(text = model.place)
                if (model.address.isNotEmpty()) {
                    TextCell(text = model.address)
                }
            }
        }
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = QuizHubTheme.colorScheme.onSurfaceVariant
        )
    }
}