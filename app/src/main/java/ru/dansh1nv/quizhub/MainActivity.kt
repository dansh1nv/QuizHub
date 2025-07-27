package ru.dansh1nv.quizhub

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import ru.dansh1nv.core.presentation.ActionEventsListener
import ru.dansh1nv.core.presentation.IntentErrorMapper
import ru.dansh1nv.core.presentation.SnackbarListener
import ru.dansh1nv.core.presentation.model.ActionEvents
import ru.dansh1nv.core.presentation.model.SnackbarEvents
import ru.dansh1nv.core.startIntentSafe
import ru.dansh1nv.core.typeAllias.UIString
import ru.dansh1nv.designsystem.theme.elements.QuizHubSnackbar
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quizhub.navigation.AppNavGraph
import android.Manifest.permission as Permission


class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private val actionEventsListener by inject<ActionEventsListener>()
    private val snackbarListener by inject<SnackbarListener>()
    private val intentErrorMapper by inject<IntentErrorMapper>()
    private val snackbarHostState by inject<SnackbarHostState>()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Permission.ACCESS_FINE_LOCATION, false) -> {
                viewModel.getLocation()
            }

            permissions.getOrDefault(Permission.ACCESS_COARSE_LOCATION, false) -> {
                viewModel.getLocation()
            }

            else -> {
                showSnackbar(resources.getString(UIString.geolocation_permissions_not_granted))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent { QuizHubApp() }
        observerGlobalEvents()
        observerSnackbarEvents()
        checkLocationPermission()
    }

    private fun observerSnackbarEvents() {
        lifecycleScope.launch {
            snackbarListener.observeSnackbarMessages().collect { event ->
                when (event) {
                    is SnackbarEvents.ShowErrorSnackbar -> {
                        showSnackbar(event.message)
                    }
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        lifecycleScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short,
                withDismissAction = false
            )
        }
    }

    private fun observerGlobalEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                actionEventsListener.observerActionEvents().collect { event ->
                    when (event) {
                        is ActionEvents.ShareEvent -> handleShareEvent(event.shareText)
                        is ActionEvents.ShowLocationEvent -> handleLocationEvent(event.locationText)
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
                showSnackbar(errorMessage)
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
                ""
            ),
            intentErrorMapper,
            onFailure = { errorMessage ->
                showSnackbar(errorMessage)
            }
        )
    }

    @Composable
    private fun QuizHubApp() {
        KoinContext {
            val navController = rememberNavController()
            QuizHubTheme(isDarkTheme = true) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = QuizHubTheme.colorScheme.surface
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AppNavGraph(
                            navController = navController,
                            onCloseApp = { this@MainActivity.finish() }
                        )
                        QuizHubSnackbar(
                            hostState = snackbarHostState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.getLocation()
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showPermissionRationale()
            }

            else -> {
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showPermissionRationale() {
        showSnackbar(resources.getString(UIString.geolocation_need_permissions))
        requestLocationPermission()
    }

    private companion object {
        const val TEXT_PLAIN = "text/plain"
    }
}