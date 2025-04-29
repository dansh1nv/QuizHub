package ru.dansh1nv.quiz.list.models.sorting

import androidx.annotation.StringRes
import ru.dansh1nv.quiz.list.R

enum class Sort(
    @StringRes
    val titleRes: Int,
) {
    ASC_DATE(R.string.sorting_asc_date),
    DESC_DATE(R.string.sorting_desc_date)
}