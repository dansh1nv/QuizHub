package ru.dansh1nv.quizhub

import android.net.Uri
import ru.dansh1nv.core.presentation.viewModel.BaseViewModel
import ru.dansh1nv.core.presentation.viewModel.Router

internal interface MainActivityRouter : Router {
    fun openDeeplink(link: Uri)
}

//Задел на будушее
internal class MainActivityViewModel : BaseViewModel<MainActivityRouter>()