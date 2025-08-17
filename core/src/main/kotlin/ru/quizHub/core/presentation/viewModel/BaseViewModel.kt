package ru.quizHub.core.presentation.viewModel

import androidx.lifecycle.ViewModel

fun interface Router {
   fun goBack()
}

abstract class BaseViewModel<R : Router> : ViewModel() {
    var router: R? = null

    open suspend fun onLaunch() {}
    open suspend fun onDispose() {}
}