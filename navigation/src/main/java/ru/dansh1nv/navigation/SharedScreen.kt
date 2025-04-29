package ru.dansh1nv.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    object QuizList : SharedScreen()
    object QuizDetails : SharedScreen()
}