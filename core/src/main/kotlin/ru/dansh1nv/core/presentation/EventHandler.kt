package ru.dansh1nv.core.presentation

interface EventHandler<EVENT: Any> {
    fun handleEvent(event: EVENT)
}