package ru.dansh1nv.core.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dansh1nv.core.presentation.ActionEventsListener
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetControllerImpl

fun coreModule() =  module {
    factoryOf(::BottomSheetControllerImpl) bind BottomSheetController::class
    singleOf(::ActionEventsListener)
}