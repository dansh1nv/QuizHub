package ru.dansh1nv.designsystem.theme.bottomsheet.model

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.colorspace.ColorModel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import ru.dansh1nv.designsystem.theme.uiKit.LocalColorScheme
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.color.CustomColorModel

/**
 * Базовая модель бщ
 *
 * @param toolbar Тулбар бщ
 * @param isDragHandleVisible Отображение иконки над тулбаром
 * @param isScrimVisible Отображение затемненного фона под бщ
 * @param isClosable Возможность закрыть бщ свайпом, системной кнопкой "Назад" или по нажатию на фон
 * @param hasTopPadding Отступ сверху
 * @param onBackClick Вызывается при нажатии системной кнопки "Назад" при полностью раскрытом бщ
 * @param onDismiss Вызывается в момент, когда бщ полностью скрылся (target и current значения Hidden)
 * @param dragHandle Параметры для ручки дергания
 */
abstract class QuizBottomSheetModel(
    open val toolbar: Toolbar? = null,
    open val isDragHandleVisible: Boolean,
    open val isScrimVisible: Boolean,
    open val isClosable: Boolean = true,
    open val hasTopPadding: Boolean = true,
    open val onBackClick: (() -> Unit)? = null,
    open val onDismiss: (() -> Unit)? = null,
    open val dragHandle: DragHandle? = null,
) {

    data class Toolbar(
        val title: String,
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
            val color: CustomColorModel = CustomColorModel.Surface,
            val onClick: (() -> Unit)? = null,
        )
    }

    /**
     * Параметры ручки дергания у бщ
     * @property paddingValues отступы
     * @property width ширина
     * @property height высота
     * @property color цвет
     * @property shape форма
     */
    data class DragHandle(
        val paddingValues: PaddingValues,
        val width: Dp,
        val height: Dp,
        val color: Color,
        val shape: Shape
    )
}