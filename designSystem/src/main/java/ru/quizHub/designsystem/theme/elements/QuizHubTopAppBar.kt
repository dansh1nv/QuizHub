package ru.quizHub.designsystem.theme.elements

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizHubTopAppBar(
    titleResId: Int,
    iconResId: Int,
    showNavigationIcon: Boolean = true,
    onNavigationClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = QuizHubTheme.colorScheme.surface,
            titleContentColor = QuizHubTheme.colorScheme.onSurface,
        ),
        title = {
            Text(
                text = stringResource(titleResId),
                style = QuizHubTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showNavigationIcon && onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = QuizHubTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}