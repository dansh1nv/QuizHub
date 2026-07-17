package ru.quizHub.quizhub

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.quizHub.core.navigation.destinations.ThemeSettingsDestination
import ru.quizHub.core.presentation.ActionEventsListener
import ru.quizHub.core.presentation.IntentErrorMapper
import ru.quizHub.core.presentation.SnackbarListener
import ru.quizHub.core.presentation.model.ActionEvents
import ru.quizHub.core.presentation.model.SnackbarEvents
import ru.quizHub.core.startIntentSafe
import ru.quizHub.designsystem.theme.elements.QuizHubSnackbar
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizhub.navigation.AppNavGraph
import ru.quizHub.quizhub.navigation.navigationBar.NavigationAppBar
import ru.quizHub.quizhub.navigation.navigationBar.NavigationAppBarItem
import ru.quizHub.quizhub.navigation.topAppBar.TopAppBar
import ru.quizHub.settings.models.ThemeModeUI
import ru.quizHub.settings.presentation.ThemeManager


class MainActivity : ComponentActivity() {

    @Suppress("unused")
    private val viewModel by viewModel<MainActivityViewModel>()
    private val actionEventsListener by inject<ActionEventsListener>()
    private val snackbarListener by inject<SnackbarListener>()
    private val intentErrorMapper by inject<IntentErrorMapper>()
    private val themeManager by inject<ThemeManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent { QuizHubApp() }
        observeGlobalEvents()
    }

    private fun observeGlobalEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                actionEventsListener.observerActionEvents().collect { event ->
                    when (event) {
                        is ActionEvents.ShareEvent -> handleShareEvent(event.shareText)
                        is ActionEvents.ShowLocationEvent -> handleLocationEvent(event.locationText)
                        is ActionEvents.AddToCalendarEvent -> handleAddToCalendarEvent(event)
                    }
                }
            }
        }
    }

    private fun handleLocationEvent(location: String) {
        val uri = location.toUri()
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        startIntentSafe(
            mapIntent,
            intentErrorMapper,
            onFailure = { errorMessage ->
                snackbarListener.showSnackbar(SnackbarEvents.ShowErrorSnackbar(errorMessage))
            }
        )
    }

    private fun handleShareEvent(shareText: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = TEXT_PLAIN
            putExtra(
                Intent.EXTRA_TEXT,
                shareText
            )
        }
        startIntentSafe(
            Intent.createChooser(
                shareIntent,
                getString(R.string.share_chooser_title)
            ),
            intentErrorMapper,
            onFailure = { errorMessage ->
                snackbarListener.showSnackbar(SnackbarEvents.ShowErrorSnackbar(errorMessage))
            }
        )
    }

    private fun handleAddToCalendarEvent(event: ActionEvents.AddToCalendarEvent) {
        val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, event.title)
            putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.beginTimeMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.endTimeMillis)
        }
        startIntentSafe(
            calendarIntent,
            intentErrorMapper,
            onFailure = { errorMessage ->
                snackbarListener.showSnackbar(SnackbarEvents.ShowErrorSnackbar(errorMessage))
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun QuizHubApp() {
        val snackbarHostState = remember { SnackbarHostState() }
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(lifecycleOwner, snackbarListener) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                snackbarListener.observeSnackbarMessages().collect { event ->
                    when (event) {
                        is SnackbarEvents.ShowErrorSnackbar -> {
                            snackbarHostState.showSnackbar(
                                message = event.message,
                                duration = SnackbarDuration.Short,
                                withDismissAction = false,
                            )
                        }
                    }
                }
            }
        }

        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        val currentTheme by themeManager.currentTheme.collectAsState()
        val themeMode by themeManager.themeMode.collectAsState()
        val systemDarkMode = isSystemInDarkTheme()

        LaunchedEffect(systemDarkMode, themeMode) {
            if (themeMode == ThemeModeUI.System) {
                themeManager.handleSystemThemeChange(systemDarkMode)
            }
        }

        val mainTabRoutes = remember {
            setOf(
                NavigationAppBarItem.QuizList.route,
                NavigationAppBarItem.Profile.route,
                NavigationAppBarItem.Settings.route,
            )
        }
        val featureToggle = remember { FeatureToggle() }
        val showsActivityTopBar = currentRoute == ThemeSettingsDestination.route
        val showsBottomBar = featureToggle.bottomNavigationEnabled &&
            currentRoute != null &&
            currentRoute in mainTabRoutes

        QuizHubTheme(
            appTheme = currentTheme,
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                topBar = {
                    TopAppBar(navController, scrollBehavior, currentRoute)
                },
                bottomBar = {
                    if (featureToggle.bottomNavigationEnabled) {
                        NavigationAppBar(navController, currentRoute)
                    }
                },
                snackbarHost = {
                    QuizHubSnackbar(
                        hostState = snackbarHostState,
                        modifier = Modifier
                    )
                },
                containerColor = QuizHubTheme.colorScheme.surface
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .then(
                            if (!showsActivityTopBar) {
                                Modifier.windowInsetsPadding(
                                    WindowInsets.statusBars.only(WindowInsetsSides.Top)
                                )
                            } else {
                                Modifier
                            }
                        )
                        .then(
                            if (!showsBottomBar) {
                                Modifier.windowInsetsPadding(
                                    WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
                                )
                            } else {
                                Modifier
                            }
                        )
                ) {
                    AppNavGraph(
                        navController = navController,
                        onCloseApp = { this@MainActivity.finish() }
                    )
                }
            }
        }
    }

    private companion object {
        const val TEXT_PLAIN = "text/plain"
    }
}
