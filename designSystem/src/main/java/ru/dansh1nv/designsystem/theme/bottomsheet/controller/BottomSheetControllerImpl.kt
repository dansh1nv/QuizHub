package ru.dansh1nv.designsystem.theme.bottomsheet.controller

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetLayer
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel

class BottomSheetControllerImpl : BottomSheetController {

    private val currentBottomSheetState = MutableStateFlow(QuizBottomSheetLayer())
    private val bottomSheetQueue = ArrayDeque<QuizBottomSheetModel>()

    override fun show(vararg bottomSheet: QuizBottomSheetModel) {
        bottomSheetQueue.addAll(bottomSheet)
        currentBottomSheetState.update {
            it.copy(
                model = bottomSheet.lastOrNull(),
                isVisible = true,
            )
        }
    }

    override fun dismiss() {
        bottomSheetQueue.clear()
        currentBottomSheetState.update { it.copy(isVisible = false) }
    }

    override fun dismissTop() {
        bottomSheetQueue.removeLastOrNull()
        val lastBottomSheet = bottomSheetQueue.lastOrNull()
        if (lastBottomSheet != null) {
            currentBottomSheetState.update {
                it.copy(model = lastBottomSheet)
            }
        } else {
            dismiss()
        }
    }

    override fun observeState(): StateFlow<QuizBottomSheetLayer> = currentBottomSheetState

    override fun clearModel() {
        currentBottomSheetState.update { it.copy(model = null) }
    }
}
