package ru.dansh1nv.designsystem.theme.elements.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import ru.dansh1nv.designsystem.R
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onClear: () -> Unit,
    placeholder: @Composable () -> Unit = {
        Text(
            text = stringResource(R.string.search_bar_placeholder),
            color = QuizHubTheme.colorScheme.onSurface,
        )
    },
    leadingIcon: @Composable (() -> Unit) = {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.search_bar_placeholder)
        )
    },
    trailingIcon: @Composable (() -> Unit)? = {
        Icon(
            painter = painterResource(R.drawable.ic_clear),
            contentDescription = null,
            modifier = Modifier.clickable { onClear.invoke() }
        )
    },
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .focusRequester(focusRequester)
                .semantics { traversalIndex = 0f },
            colors = SearchBarDefaults.colors(
                containerColor = QuizHubTheme.colorScheme.surface
            ),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon.takeIf { query.isNotEmpty() },
                    expanded = false,
                    onExpandedChange = {},
                    colors = inputFieldColors(
                        focusedTextColor = QuizHubTheme.colorScheme.onSurface,
                        unfocusedTextColor = QuizHubTheme.colorScheme.onSurface,
                        focusedLeadingIconColor = QuizHubTheme.colorScheme.onSurface,
                        unfocusedLeadingIconColor = QuizHubTheme.colorScheme.onSurface,
                    )
                )
            },
            expanded = false,
            onExpandedChange = {},
            content = {},
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }
}