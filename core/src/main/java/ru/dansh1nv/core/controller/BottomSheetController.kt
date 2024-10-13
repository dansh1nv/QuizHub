package ru.dansh1nv.core.controller

import kotlinx.coroutines.flow.StateFlow
import ru.dansh1nv.designsystem.theme.bottomsheet.model.BottomSheetLayer
import ru.dansh1nv.designsystem.theme.bottomsheet.model.BottomSheetModel

interface BottomSheetController {

    /**
     * Открывает бщ
     */
    fun show(vararg bottomSheet: BottomSheetModel)

    /**
     * Закрывает бщ
     */
    fun dismiss()

    /**
     * Закрывает верхний бщ
     */
    fun dismissTop()

    /**
     * Возвращает стейт бщ
     */
    fun observeState(): StateFlow<BottomSheetLayer>
}