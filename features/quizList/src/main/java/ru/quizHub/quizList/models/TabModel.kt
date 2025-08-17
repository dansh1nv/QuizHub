package ru.quizHub.quizList.models

import androidx.annotation.StringRes
import ru.quizHub.quizList.R

enum class TabModel(
    @StringRes
    val titleRes: Int,
    val index: Int
) {
    ALL(R.string.tab_item_all, 0),
    FAVOURITE(R.string.tab_item_favourite, 1)
}