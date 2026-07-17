package ru.quizHub.quizhub.devtools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.quizHub.core.featureToggle.FeatureToggleCatalog
import ru.quizHub.core.featureToggle.FeatureToggleItem
import ru.quizHub.core.featureToggle.get
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun DevToolsScreen(
    viewModel: DevToolsViewModel = koinViewModel(),
) {
    val toggles by viewModel.toggles.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "Feature toggles",
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 16.dp),
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(FeatureToggleCatalog.items, key = { it.key }) { item ->
                FeatureToggleRow(
                    item = item,
                    enabled = toggles.get(item.key),
                    onCheckedChange = { checked ->
                        viewModel.onToggleChanged(item.key, checked)
                    },
                )
                HorizontalDivider(color = QuizHubTheme.colorScheme.outlineVariant)
            }
        }

        Button(
            onClick = viewModel::onResetToDefaults,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            Text(text = "Сбросить к дефолтам")
        }
    }
}

@Composable
private fun FeatureToggleRow(
    item: FeatureToggleItem,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
        ) {
            Text(
                text = item.title,
                style = QuizHubTheme.typography.titleMedium,
                color = QuizHubTheme.colorScheme.onSurface,
            )
            Text(
                text = item.description,
                style = QuizHubTheme.typography.bodyMedium,
                color = QuizHubTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(
            checked = enabled,
            onCheckedChange = onCheckedChange,
        )
    }
}