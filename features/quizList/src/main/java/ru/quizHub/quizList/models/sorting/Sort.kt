package ru.quizHub.quizList.models.sorting

import androidx.annotation.StringRes
import ru.quizHub.quizList.R

enum class Sort(
    @StringRes
    val titleRes: Int,
) {
    ASC_DATE(R.string.sorting_asc_date),
    DESC_DATE(R.string.sorting_desc_date)
}