package ru.dansh1nv.quizhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quizhub.navigation.AppNavGraph


class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent { QuizHubApp() }
    }

    @Composable
    private fun QuizHubApp() {
        KoinContext {
            val navController = rememberNavController()
            QuizHubTheme(isDarkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
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