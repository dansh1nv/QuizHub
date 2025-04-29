package ru.dansh1nv.quiz.list.models

import androidx.annotation.StringRes
import ru.dansh1nv.designsystem.theme.tag.TagColor
import ru.dansh1nv.quiz.list.R

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