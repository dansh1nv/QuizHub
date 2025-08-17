package ru.quizHub.core.presentation

interface EventHandler<EVENT: UIEvent> {
    fun handleEvent(event: EVENT)
}