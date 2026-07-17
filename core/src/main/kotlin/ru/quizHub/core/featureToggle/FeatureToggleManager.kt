package ru.quizHub.core.featureToggle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.quizHub.core.datastore.AppDataStore

class FeatureToggleManager(
    private val appDataStore: AppDataStore,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val state: StateFlow<FeatureToggleState> = appDataStore.dataFlow
        .map { it.featureToggles }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = FeatureToggleState(),
        )

    suspend fun set(key: FeatureToggleKey, enabled: Boolean) {
        appDataStore.update {
            copy(featureToggles = featureToggles.with(key, enabled))
        }
    }

    suspend fun resetToDefaults() {
        appDataStore.update {
            copy(featureToggles = FeatureToggleState())
        }
    }
}