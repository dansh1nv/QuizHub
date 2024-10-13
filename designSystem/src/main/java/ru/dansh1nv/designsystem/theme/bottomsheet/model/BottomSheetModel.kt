package ru.dansh1nv.designsystem.theme.bottomsheet.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import ru.dansh1nv.designsystem.theme.QuizHubTheme

/**
 * @param toolbar Тулбар бщ
 * @param isDragHandleVisible Отображение иконки над тулбаром
 * @param isScrimVisible Отображение затемненного фона под бщ
 * @param isClosable Возможность закрыть бщ свайпом, системной кнопкой "Назад" или по нажатию на фон
 * @param onBackClick Вызывается при нажатии системной кнопки "Назад" при полностью раскрытом бщ
 * @param onDismiss Вызывается в момент, когда бщ полностью скрылся (target и current значения Hidden)
 */
abstract class BottomSheetModel(
    open val toolbar: Toolbar? = null,
    open val isDragHandleVisible: Boolean,
    open val isScrimVisible: Boolean,
    open val isClosable: Boolean = true,
    open val onBackClick: (() -> Unit)? = null,
    open val onDismiss: (() -> Unit)? = null,
) {

    data class Toolbar(
        @StringRes val title: Int,
        val titleType: TitleType = TitleType.SINGLE,
        val leadIcon: IconModel? = null,
        val trailIcon: IconModel? = null,
    ) {

        enum class TitleType(
            val maxLines: Int,
        ) {
            SINGLE(1),
            DOUBLE(2);

            val textStyle: TextStyle
                @Composable
                get() = when (this) {
                    SINGLE -> QuizHubTheme.typography.titleLarge
                    DOUBLE -> QuizHubTheme.typography.titleMedium
                }
        }
        data class IconModel(
            @DrawableRes val iconRes: Int,
            val onClick: (() -> Unit)? = null,
        )
    }

    object FeatureDisabled : BottomSheetModel(isDragHandleVisible = false, isScrimVisible = true)
}