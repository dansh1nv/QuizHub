package ru.dansh1nv.core.presentation.viewModel

import androidx.lifecycle.viewModelScope
import org.koin.core.component.KoinComponent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import ru.dansh1nv.core.presentation.EventHandler
import ru.dansh1nv.core.presentation.ScreenState
import ru.dansh1nv.core.presentation.SideEffect
import ru.dansh1nv.core.presentation.UIEvent

abstract class BaseMviViewModel<STATE : ScreenState, SIDE_EFFECT : SideEffect, EVENT : UIEvent>(
    initialState: STATE
) : ContainerHost<STATE, SIDE_EFFECT>,
    EventHandler<EVENT>,
    BaseViewModel<Router>(),
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