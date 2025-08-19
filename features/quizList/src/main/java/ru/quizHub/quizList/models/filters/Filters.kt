package ru.quizHub.quizList.models.filters

import androidx.annotation.StringRes
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.item.Organization

enum class Filters(
    @StringRes
    val titleRes: Int,
    val organization: Organization,
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