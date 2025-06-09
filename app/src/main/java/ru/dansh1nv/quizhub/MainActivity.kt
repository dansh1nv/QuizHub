package ru.dansh1nv.quizhub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import ru.dansh1nv.core.presentation.model.ActionEvents
import ru.dansh1nv.common.startIntentSafe
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quizhub.navigation.AppNavGraph


class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private val actionEventsListener by inject<ActionEventsListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent { QuizHubApp() }
        observerGlobalEvents()
    }

    private fun observerGlobalEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                actionEventsListener.observerActionEvents().collect { event ->
                    when (event) {
                        is ActionEvents.ShareEvent -> handleShareEvent(event.shareText)
                    }
                }
            }
        }
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
            )
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