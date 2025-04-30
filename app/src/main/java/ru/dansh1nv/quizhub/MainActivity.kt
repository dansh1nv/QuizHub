package ru.dansh1nv.quizhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.presentation.composable.QuizListScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext {
                QuizHubTheme(isDarkTheme = true) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = QuizHubTheme.colorScheme.surface
                    ) {
                        QuizListScreen()
                    }
                }
            }
        }
    }
}