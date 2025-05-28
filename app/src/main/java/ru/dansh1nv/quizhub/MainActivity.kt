package ru.dansh1nv.quizhub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz_list_domain.models.common.ActionEvents
import ru.dansh1nv.quiz_list_domain.models.common.ActionEventsListener
import ru.dansh1nv.quizhub.navigation.AppNavGraph


class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private val actionEventsListener by inject<ActionEventsListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent { QuizHubApp() }
        observerGlobalEvents()
    }

    private fun observerGlobalEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                actionEventsListener.observerActionEvents().collect { event ->
                    when(event) {
                        is ActionEvents.ActionEvent -> handleShareEvent(event)
                    }
                }
            }
        }
    }

    private fun handleShareEvent(event: ActionEvents.ActionEvent) {
        val shareEvent = event.shareEvent
        val shareText = buildString {
            append("${getString(R.string.join_quiz)} ${shareEvent.title}\n\n")
            shareEvent.date?.let { append("${getString(R.string.date_quiz)} $it\n") }
            shareEvent.time?.let { append("${getString(R.string.time_day_quiz)} $it\n") }
            shareEvent.teamSize?.let { append("${getString(R.string.team_size_quiz)} $it\n") }
            shareEvent.address?.let { append("${getString(R.string.address_quiz)} $it\n") }
            shareEvent.place?.let { append("${getString(R.string.place_quiz)} $it\n") }
            append("${getString(R.string.price_quiz)} ${shareEvent.price}\n")
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        startActivity(
            Intent.createChooser(
                shareIntent,
                getString(R.string.share_quiz_title)
            )
        )
    }

    @Composable
    private fun QuizHubApp() {
        KoinContext {
            val navController = rememberNavController()
            QuizHubTheme(isDarkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding(),
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
}