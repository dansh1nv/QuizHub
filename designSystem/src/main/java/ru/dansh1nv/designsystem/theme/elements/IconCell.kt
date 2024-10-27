package ru.dansh1nv.designsystem.theme.elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.QuizHubTheme

@Composable
fun IconCell(
    @DrawableRes iconRes: Int
) {
    Icon(
        painter = painterResource(id = iconRes),
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp),
        tint = QuizHubTheme.colorScheme.onSurface,
        contentDescription = null,
    )
}