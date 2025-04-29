package ru.dansh1nv.designsystem.theme.bottomsheet.controller

import kotlinx.coroutines.flow.StateFlow
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetLayer
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel

/**
 * Контроллер для QuizModalBottomSheet
 */
interface BottomSheetController {

    /**
     * Открывает бщ
     */
    fun show(vararg bottomSheet: QuizBottomSheetModel)

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
    fun observeState(): StateFlow<QuizBottomSheetLayer>

    /**
     * Очищает model в стейте бщ
     */
    fun clearModel()
}