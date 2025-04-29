package ru.dansh1nv.quiz.details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class QuizDetailsScreen() : Screen {
    @Composable
    override fun Content() {
        Column {
            Text(text = "Это экран детализации квиза")
        }
    }

}