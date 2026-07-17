package ru.quizHub.quizhub.devtools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.quizHub.core.featureToggle.FeatureToggleKey
import ru.quizHub.core.featureToggle.FeatureToggleManager
import ru.quizHub.core.featureToggle.FeatureToggleState

class DevToolsViewModel(
    private val featureToggleManager: FeatureToggleManager,
) : ViewModel() {

    val toggles: StateFlow<FeatureToggleState> = featureToggleManager.state

    fun onToggleChanged(key: FeatureToggleKey, enabled: Boolean) {
        viewModelScope.launch {
            featureToggleManager.set(key, enabled)
        }
    }

    fun onResetToDefaults() {
        viewModelScope.launch {
            featureToggleManager.resetToDefaults()
        }
    }
}