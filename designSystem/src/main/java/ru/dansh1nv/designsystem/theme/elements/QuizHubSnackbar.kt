package ru.dansh1nv.designsystem.theme.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun QuizHubSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier,
    type: SnackbarType = SnackbarType.ERROR
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackbarData ->
        val (containerColor, contentColor) = when (type) {
            SnackbarType.ERROR -> Pair(
                QuizHubTheme.colorScheme.errorContainer,
                QuizHubTheme.colorScheme.onErrorContainer
            )

            SnackbarType.SUCCESS -> {
                TODO()
            }

            SnackbarType.WARNING -> {
                TODO()
            }

            SnackbarType.INFO -> {
                TODO()
            }
        }
        Snackbar(
            modifier = Modifier.padding(8.dp),
            containerColor = containerColor,
            contentColor = contentColor,
            shape = QuizHubTheme.shapes.shape8dp
        ) {
            Text(
                text = snackbarData.visuals.message,
                style = QuizHubTheme.typography.bodyMedium
            )
        }
    }
}