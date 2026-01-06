package ru.quizHub.designsystem.theme.uiKit

import android.app.Activity
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }
internal val LocalTypography = staticCompositionLocalOf { QuizHubTypography }
internal val LocalShapes = staticCompositionLocalOf { quizHubRoundedShapes }
internal val LocalIconTintColor = compositionLocalOf { Color.Black }
internal val LocalTagColor = compositionLocalOf { customColor() }
internal val IsDarkMode = staticCompositionLocalOf { false }
internal val LocalAppTheme = compositionLocalOf { AppTheme(isDarkMode = false) }

object QuizHubTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: QuizHubShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val customColor: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalTagColor.current

    val isDarkMode: Boolean
        @Composable
        @ReadOnlyComposable
        get() = IsDarkMode.current
}

@Composable
fun QuizHubTheme(
    appTheme: AppTheme,
    dynamicColor: Boolean = true,
    shapes: QuizHubShapes = QuizHubTheme.shapes,
    typography: Typography = QuizHubTheme.typography,
    content: @Composable () -> Unit
) {
    val isDarkTheme = appTheme.isDarkMode
    val isHighContrast = appTheme.isHighContrast

    val colorScheme = when {
        isHighContrast -> {
            if (isDarkTheme) highContrastDarkColorScheme
            else highContrastLightColorScheme
        }

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> darkScheme
        else -> lightScheme
    }
    val tintColor = colorScheme.onSurfaceVariant
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDarkTheme
                isAppearanceLightNavigationBars = !isDarkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalAppTheme provides appTheme,
        LocalColorScheme provides colorScheme,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        LocalIconTintColor provides tintColor,
        IsDarkMode provides isDarkTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = {
                ProvideTextStyle(value = typography.bodyLarge, content = content)
            }
        )
    }
}