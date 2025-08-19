package ru.quizHub.quizList.models

import androidx.annotation.StringRes
import ru.quizHub.designsystem.theme.tag.TagColor
import ru.quizHub.quizList.R

enum class TagModel(
    @StringRes
    val title: Int,
    val tag: TagColor,
) {
    QUIZ_PLEASE(
        title = R.string.filter_id_quiz_please,
        tag = TagColor.ORANGE
    ),
    SHAKER_QUIZ(
        title = R.string.filter_id_shaker_quiz,
        tag = TagColor.PURPLE
    ),
    SQUIZ(
        title = R.string.filter_id_squiz,
        tag = TagColor.BLUE
    ),
}