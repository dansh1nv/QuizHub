package ru.dansh1nv.designsystem.theme.bottomsheet.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.color.toIconColor
import ru.dansh1nv.designsystem.theme.utils.modifier.condition

@Composable
internal fun QuizModalBottomSheetToolbar(
    model: QuizBottomSheetModel.Toolbar,
) {
    Row(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 12.dp,
            start = 12.dp,
            end = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        model.leadIcon?.let { icon ->
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(24.dp)
                    .condition(
                        condition = (icon.onClick != null),
                        ifTrue = { clickable(onClick = { icon.onClick?.invoke() }) },
                    ),
                painter = painterResource(icon.iconRes),
                tint = icon.color.toIconColor(),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier.weight(1F),
            text = model.title,
            style = model.titleType.textStyle,
            maxLines = model.titleType.maxLines,
            overflow = TextOverflow.Ellipsis,
            color = QuizHubTheme.colorScheme.onSurface,
        )
        model.trailIcon?.let { icon ->
            Icon(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .size(24.dp)
                    .condition(
                        condition = (icon.onClick != null),
                        ifTrue = { clickable(onClick = { icon.onClick?.invoke() }) },
                    ),
                painter = painterResource(icon.iconRes),
                tint = icon.color.toIconColor(),
                contentDescription = null,
            )
        }
    }
}
