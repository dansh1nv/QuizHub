package ru.dansh1nv.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.koin.core.component.KoinComponent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

abstract class BaseMviScreenModel<STATE : ScreenState, SIDE_EFFECT : SideEffect, EVENT : UIEvent>(
    initialState: STATE
) : ContainerHost<STATE, SIDE_EFFECT>,
    EventHandler<EVENT>,
    ViewModel(),
    KoinComponent {

    override val container = viewModelScope.container<STATE, SIDE_EFFECT>(initialState)

    protected fun updateState(newState: STATE.() -> STATE) {
        intent {
            reduce {
                newState(state)
            }
        }
    }

    protected fun postSideEffect(sideEffect: SIDE_EFFECT) {
        intent {
            postSideEffect(sideEffect)
        }
    }
}