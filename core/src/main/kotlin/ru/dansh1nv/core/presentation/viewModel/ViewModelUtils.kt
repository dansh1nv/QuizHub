package ru.dansh1nv.core.presentation.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified VM : BaseViewModel<Router>> viewModel(noinline parameters: ParametersDefinition? = null): VM {
    return viewModel(Router { }, parameters)
}

@Composable
inline fun <reified VM : BaseViewModel<R>, R : Router> viewModel(
    router: R,
    noinline parameters: ParametersDefinition? = null,
): VM {
    val viewModel = koinViewModel<VM>(parameters = parameters)
    val scope = rememberCoroutineScope()

    DisposableEffect(router) {
        viewModel.router = router
        onDispose {
            viewModel.router = null
        }
    }

    DisposableEffect(viewModel) {
        scope.launch { viewModel.onLaunch() }
        onDispose {
            scope.launch { viewModel.onDispose() }
        }
    }

    return viewModel
}