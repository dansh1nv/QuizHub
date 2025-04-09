package ru.dansh1nv.designsystem.theme.bottomsheet

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.dansh1nv.common.orFalse
import ru.dansh1nv.common.orTrue
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.dansh1nv.designsystem.theme.bottomsheet.custom.QuizBottomSheetContent
import ru.dansh1nv.designsystem.theme.bottomsheet.info.QuizBottomSheetInfoContent
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetLayer
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizInfoBottomSheetModel
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.modifier.condition
import wildberries.wbpartners.utils.keyboard.keyboardAsState
import ru.dansh1nv.designsystem.theme.utils.updateEffect.UpdateEffect

/**
 * Базовый BottomSheet
 *
 * @param controller Объект класса BottomSheetController
 * @param onTargetHideBottomSheet Вызывается при закрытии бщ
 * @param onTargetShowBottomSheet Вызывается при открытии бщ
 * @param onHideBottomSheet Вызывается когда бщ закрылся
 * @param onShowBottomSheet Вызывается когда бщ открылся
 * @param customBottomSheetContent Контент для WbBottomSheetMode.Custom
 * @param infoScrollState Состояние скролла для WbInfoBottomSheetModel
 *
 * P.S. Колбеки onTargetHideBottomSheet, onTargetShowBottomSheet, onHideBottomSheet,
 * onShowBottomSheet рекомендуется использовать только для всяких служебных целей по типу
 * скрытия клавиатуры и тд, для бизнес логики есть колбеки в [QuizBottomSheetModel]
 */
@Composable
fun QuizModalBottomSheet(
    controller: BottomSheetController,
    onTargetHideBottomSheet: () -> Unit = {},
    onTargetShowBottomSheet: () -> Unit = {},
    onHideBottomSheet: () -> Unit = {},
    onShowBottomSheet: () -> Unit = {},
    sheetGesturesEnabled: Boolean = true,
    infoScrollState: ScrollState = rememberScrollState(),
    customBottomSheetContent: @Composable (ColumnScope.(QuizBottomSheetModel) -> Unit)? = null,
) {
    val scope = rememberCoroutineScope()
    val contentState by controller.observeState().collectAsStateWithLifecycle()
    var bottomSheetCurrentValue by remember { mutableStateOf(ModalBottomSheetValue.Hidden) }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            bottomSheetCurrentValue = it
            confirmBottomSheetValueChange(
                value = it,
                isClosable = contentState.model?.isClosable.orTrue(),
            )
        }
    )

    val bottomSheetTargetValue = bottomSheetState.targetValue

    val isBottomSheetTargetExpanded = (bottomSheetTargetValue == ModalBottomSheetValue.Expanded)
    val isBottomSheetCurrentExpanded = (bottomSheetCurrentValue == ModalBottomSheetValue.Expanded)
    val isBottomSheetRequireVisible = contentState.isVisible

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 0.dp,
        sheetGesturesEnabled = sheetGesturesEnabled,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = getScrimColor(contentState.model?.isScrimVisible.orFalse()),
        sheetContent = {
            if (contentState.model?.isDragHandleVisible.orFalse()) {
                DragHandle(contentState.model?.dragHandle)
            }
            BottomSheetContent(
                contentState = contentState,
                infoScrollState = infoScrollState,
                isBottomSheetExpanded = isBottomSheetCurrentExpanded && isBottomSheetTargetExpanded,
                customBottomSheetContent = customBottomSheetContent,
            )
        },
        content = {},
    )

    BackHandler(isBottomSheetTargetExpanded) {
        contentState.model?.onBackClick?.invoke()
            ?: scope.launch {
                if (contentState.model?.isClosable.orTrue()) {
                    bottomSheetState.hide()
                }
            }
    }

    LaunchedEffect(isBottomSheetRequireVisible) {
        toggleBottomSheetState(
            bottomSheetState = bottomSheetState,
            scope = scope,
            isBottomSheetRequireVisible = isBottomSheetRequireVisible,
            onHideComplete = controller::clearModel,
        )
    }

    LaunchedEffect(bottomSheetTargetValue) {
        when (bottomSheetTargetValue) {
            ModalBottomSheetValue.Hidden -> onTargetHideBottomSheet()
            ModalBottomSheetValue.Expanded -> onTargetShowBottomSheet()
            else -> {}
        }
    }

    LaunchedEffect(bottomSheetCurrentValue) {
        when (bottomSheetCurrentValue) {
            ModalBottomSheetValue.Hidden -> onHideBottomSheet()
            ModalBottomSheetValue.Expanded -> onShowBottomSheet()
            else -> {}
        }
    }

    UpdateEffect(bottomSheetTargetValue, bottomSheetCurrentValue) {
        if (bottomSheetTargetValue == ModalBottomSheetValue.Hidden
            && bottomSheetCurrentValue == ModalBottomSheetValue.Hidden
        ) {
            controller.dismiss()
            contentState.model?.onDismiss?.invoke()
        }
    }
}

@Composable
private fun BottomSheetContent(
    contentState: QuizBottomSheetLayer,
    isBottomSheetExpanded: Boolean,
    infoScrollState: ScrollState,
    customBottomSheetContent: @Composable (ColumnScope.(QuizBottomSheetModel) -> Unit)?,
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val isKeyboardVisible by keyboardAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = QuizHubTheme.colorScheme.surfaceContainer,
                shape = QuizHubTheme.shapes.shapeTop16dp,
            )
            .condition(
                condition = contentState.model?.hasTopPadding.orTrue(),
                ifTrue = { heightIn(max = (screenHeightDp * 0.9f).dp) },
                ifFalse = { heightIn(max = screenHeightDp.dp) },
            )
            .navigationBarsPadding()
            .condition(
                condition = (isBottomSheetExpanded && !isKeyboardVisible),
                ifTrue = { animateContentSize() },
            ),
    ) {
        val model = contentState.model ?: return
        if (model is QuizInfoBottomSheetModel) {
            QuizBottomSheetInfoContent(
                modifier = Modifier.imePadding(),
                model = model,
                scrollState = infoScrollState,
            )
        } else {
            QuizBottomSheetContent(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .imePadding(),
                model = model,
                content = { customBottomSheetContent?.invoke(this, model) },
            )
        }
    }
}

@Composable
private fun ColumnScope.DragHandle(dragHandle: QuizBottomSheetModel.DragHandle? = null) {
    val notNullDragHandle = dragHandle ?: QuizBottomSheetModel.DragHandle(
        paddingValues = PaddingValues(vertical = 3.dp),
        width = 27.dp,
        height = 3.dp,
        color = QuizHubTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(2.dp)
    )
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(notNullDragHandle.paddingValues)
            .size(
                width = notNullDragHandle.width,
                height = notNullDragHandle.height
            )
            .background(
                color = notNullDragHandle.color,
                shape = notNullDragHandle.shape,
            ),
    )
}

@Composable
private fun getScrimColor(isScrimVisible: Boolean): Color {
    return if (isScrimVisible) {
        QuizHubTheme.colorScheme.background.copy(alpha = if (QuizHubTheme.isDarkMode) 0.68F else 0.32F)
    } else {
        Color.Unspecified
    }
}

private fun confirmBottomSheetValueChange(
    value: ModalBottomSheetValue,
    isClosable: Boolean,
): Boolean {
    return if (!isClosable) {
        (value != ModalBottomSheetValue.Hidden)
    } else {
        true
    }
}

private fun toggleBottomSheetState(
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    isBottomSheetRequireVisible: Boolean,
    onHideComplete: () -> Unit,
) {
    if (isBottomSheetRequireVisible) {
        scope.launch { bottomSheetState.show() }
    } else {
        scope.launch {
            bottomSheetState.hide()
            onHideComplete()
        }
    }
}
