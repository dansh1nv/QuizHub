package ru.dansh1nv.core.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import org.orbitmvi.orbit.ContainerHost

abstract class BaseMviScreenModel<STATE : Any, SIDE_EFFECT : Any, EVENT : Any>(initialState: STATE) :
    ContainerHost<STATE, SIDE_EFFECT>,
    EventHandler<EVENT>,
    ScreenModel {

    override val container = container<STATE, SIDE_EFFECT>(initialState)

    protected fun updateState(newState: (STATE) -> STATE) {
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