package ru.quizHub.quizhub

import android.net.Uri
import ru.quizHub.core.presentation.viewModel.BaseViewModel
import ru.quizHub.core.presentation.viewModel.Router

internal interface MainActivityRouter : Router {
    fun openDeeplink(link: Uri)
}

//Задел на будушее
internal class MainActivityViewModel : BaseViewModel<MainActivityRouter>()