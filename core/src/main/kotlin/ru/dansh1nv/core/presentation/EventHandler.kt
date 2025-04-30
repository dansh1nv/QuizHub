package ru.dansh1nv.core.presentation

interface EventHandler<EVENT: UIEvent> {
    fun handleEvent(event: EVENT)
}