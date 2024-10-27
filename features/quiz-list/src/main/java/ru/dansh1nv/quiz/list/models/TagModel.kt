package ru.dansh1nv.quiz.list.models

import androidx.annotation.StringRes
import ru.dansh1nv.designsystem.theme.tag.TagColor
import ru.dansh1nv.quiz.list.R

enum class TagModel(
    @StringRes
    val title: Int,
    val color: TagColor,
) {
    QUIZ_PLEASE(
        title = R.string.filter_id_quiz_please,
        color = TagColor.ORANGE
    ),
    SHAKER_QUIZ(
        title = R.string.filter_id_shaker_quiz,
        color = TagColor.PURPLE
    ),
    SQUIZ(
        title = R.string.filter_id_squiz,
        color = TagColor.BLUE
    ),
}