package ru.dansh1nv.core.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetControllerImpl

fun uiModule() =  module {
    factoryOf(::BottomSheetControllerImpl) bind BottomSheetController::class
}