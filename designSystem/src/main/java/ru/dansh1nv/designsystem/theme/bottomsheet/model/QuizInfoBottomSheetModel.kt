package ru.dansh1nv.designsystem.theme.bottomsheet.model

import androidx.annotation.DrawableRes

/**
 * Частный случай модели бщ
 *
 * @param toolbar Тулбар бщ
 * @param isDragHandleVisible Отображение иконки над тулбаром
 * @param isScrimVisible Отображение затемненного фона под бщ
 * @param isClosable Возможность закрыть бщ свайпом, системной кнопкой "Назад" или по нажатию на фон
 * @param onBackClick Вызывается при нажатии системной кнопки "Назад" при полностью раскрытом бщ
 * @param onDismiss Вызывается в момент, когда бщ полностью скрылся (target и current значения Hidden)
 * @param needBottomPadding Добавляет отступ внизу
 * @param text Основной текст бщ
 * @param actionButton Кнопка действия
 * @param imgResId Ресурс основного изображения
 * @param onLinkClick Вызывается при нажатии на ссылку в основном тексте
 */
data class QuizInfoBottomSheetModel(
    override val toolbar: Toolbar? = null,
    override val isDragHandleVisible: Boolean,
    override val isScrimVisible: Boolean,
    override val isClosable: Boolean = true,
    override val onBackClick: (() -> Unit)? = null,
    override val onDismiss: (() -> Unit)? = null,
    val needBottomPadding: Boolean = false,
    val text: String,
    val actionButton: ActionButton? = null,
    @DrawableRes val imgResId: Int? = null,
    val onLinkClick: ((String) -> Unit)? = null,
) : QuizBottomSheetModel(
    toolbar = toolbar,
    isDragHandleVisible = isDragHandleVisible,
    isScrimVisible = isScrimVisible,
    isClosable = isClosable,
) {

    data class ActionButton(
        val text: String,
        @DrawableRes val leftIconRes: Int? = null,
        @DrawableRes val rightIconRes: Int? = null,
        val onClick: () -> Unit,
    )
}