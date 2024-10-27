package ru.dansh1nv.quiz.list.models.filters

import androidx.annotation.StringRes
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.Organization

enum class Filters(
    @StringRes
    val titleRes: Int,
    val organization: Organization
) {
    QUIZ_PLEASE(
        titleRes = R.string.filter_id_quiz_please,
        organization = Organization.QUIZ_PLEASE,
    ),
    SQUIZ(
        titleRes = R.string.filter_id_squiz,
        organization = Organization.SQUIZ,
    ),
    SHAKER_QUIZ(
        titleRes = R.string.filter_id_shaker_quiz,
        organization = Organization.SHAKER_QUIZ,
    )
}