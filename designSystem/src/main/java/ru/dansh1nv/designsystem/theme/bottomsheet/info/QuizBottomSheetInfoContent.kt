package ru.dansh1nv.designsystem.theme.bottomsheet.info

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.bottomsheet.custom.QuizBottomSheetContent
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizInfoBottomSheetModel

@Composable
internal fun QuizBottomSheetInfoContent(
    modifier: Modifier = Modifier,
    model: QuizInfoBottomSheetModel,
    scrollState: ScrollState,
) {
    val needBottomPadding = model.needBottomPadding
    Box(modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(
                    top = 12.dp,
                    bottom = if (model.actionButton != null) {
                        72.dp
                    } else {
                        0.dp
                    },
                ),
        ) {
            model.imgResId?.let { InfoImage(it) }
            QuizBottomSheetContent(
                model = model,
                needBottomPadding = needBottomPadding,
                content = {
                    Text(
                        text = model.text,
                    )
                },
            )
        }
    }
}

@Composable
private fun InfoImage(
    @DrawableRes imgResId: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(180.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(imgResId),
            contentDescription = null,
        )
    }
}