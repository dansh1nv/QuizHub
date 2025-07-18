package ru.dansh1nv.quizhub

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
fun CustomSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            modifier = Modifier.padding(8.dp),
            containerColor = QuizHubTheme.colorScheme.errorContainer,
            contentColor = QuizHubTheme.colorScheme.onErrorContainer,
            shape = QuizHubTheme.shapes.shape8dp
        ) {
            Text(
                text = snackbarData.visuals.message,
                style = QuizHubTheme.typography.bodyMedium
            )
        }
    }
}