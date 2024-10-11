package ru.dansh1nv.quizhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.presentation.QuizListScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizHubTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = QuizHubTheme.colorScheme.surface
                ) {
                    Navigator(
                        screen = QuizListScreen(),
                    )
                }
            }
        }
    }
}