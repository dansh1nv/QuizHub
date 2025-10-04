package ru.quizHub.designsystem.theme.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable

@Composable
fun QuizImage(
    modifier: Modifier = Modifier,
    url: String?,
    borderShape: Shape = QuizHubTheme.shapes.shape16dp,
    contentScale: ContentScale = ContentScale.Crop,
    @DrawableRes placeholder: Int = UIDrawable.ic_no_image,
    onError: (() -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        modifier = modifier.clip(borderShape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = QuizHubTheme.colorScheme.secondary,
                strokeWidth = 2.dp,
            )
        },
        error = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    contentDescription = null,
                    painter = painterResource(placeholder),
                    colorFilter = ColorFilter.tint(QuizHubTheme.colorScheme.onSecondary)
                )
            }
        },
        onError = { onError?.invoke() }
    )
}