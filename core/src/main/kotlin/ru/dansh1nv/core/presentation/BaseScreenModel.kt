package ru.dansh1nv.core.presentation

import cafe.adriel.voyager.core.model.ScreenModel

abstract class BaseScreenModel<EVENT : UIEvent> : ScreenModel {

    abstract fun onUIEvent(event: EVENT)

}