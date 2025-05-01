package ru.dansh1nv.core.presentation

import androidx.lifecycle.ViewModel

interface Router {
    fun navigateTo(route: String)
    fun navigateBack()
    fun popUpTo(route: String, inclusive: Boolean)
}

abstract class BaseViewModel<T : Router> : ViewModel() {
    var router: T? = null

    open suspend fun onLaunch() {}
    open suspend fun onDispose() {}
}