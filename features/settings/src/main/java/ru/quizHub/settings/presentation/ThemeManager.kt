package ru.quizHub.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.quizHub.designsystem.theme.uiKit.AppTheme
import ru.quizHub.settings.interactors.ThemePreferencesInteractor
import ru.quizHub.settings.mappers.ThemeModeMapperUI
import ru.quizHub.settings.models.ThemeModeUI

class ThemeManager(
    private val themePrefInteractor: ThemePreferencesInteractor,
    private val mapper: ThemeModeMapperUI
) : ViewModel() {
    private val _systemDarkMode = MutableStateFlow(false)

    private val _themeMode = MutableStateFlow<ThemeModeUI>(ThemeModeUI.System)
    val themeMode: StateFlow<ThemeModeUI> = _themeMode.asStateFlow()

    private val _currentTheme = MutableStateFlow(AppTheme(isDarkMode = false))
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()

    init {
        loadInitialTheme()
        setupThemeReactions()
    }

    fun updateThemeMode(mode: ThemeModeUI) {
        _themeMode.update { mode }

        viewModelScope.launch {
            val domainMode = mapper.mapToThemeMode(mode)
            themePrefInteractor.saveThemeMode(domainMode)
        }
    }

    fun handleSystemThemeChange(isSystemDark: Boolean) {
        _systemDarkMode.update { isSystemDark }
    }

    private fun loadInitialTheme() {
        viewModelScope.launch {
            themePrefInteractor.getThemeMode()
                .collect { domainMode ->
                    val themeModeUI = mapper.mapToThemeModeUI(domainMode)
                    _themeMode.update { themeModeUI }
                }
        }
    }

    private fun setupThemeReactions() {
        combine(
            flow = _themeMode,
            flow2 = _systemDarkMode,
            transform = ::calculateAppTheme
        )
            .distinctUntilChanged()
            .onEach { currentTheme ->
                _currentTheme.update { currentTheme }
            }
            .launchIn(viewModelScope)
    }

    private fun calculateAppTheme(selectedMode: ThemeModeUI, isSystemDark: Boolean): AppTheme {
        val shouldUseDark = when (selectedMode) {
            ThemeModeUI.Light -> false
            ThemeModeUI.Dark -> true
            ThemeModeUI.System -> isSystemDark
            ThemeModeUI.HighContrast -> true
        }

        return AppTheme(
            isDarkMode = shouldUseDark,
            isHighContrast = selectedMode == ThemeModeUI.HighContrast
        )
    }
}