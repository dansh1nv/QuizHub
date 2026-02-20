package ru.quizHub.quizhub.utils

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import timber.log.Timber

class TimberLogger(level: Level = Level.DEBUG) : Logger(level) {

    override fun display(level: Level, msg: String) {
        when (level) {
            Level.DEBUG -> Timber.d(msg)
            Level.INFO -> Timber.i(msg)
            Level.ERROR -> Timber.e(msg)
            Level.WARNING -> Timber.w(msg)
            Level.NONE -> Unit
        }
    }
}