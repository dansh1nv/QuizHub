package ru.quizHub.quizList.models.item

import androidx.annotation.StringRes
import ru.quizHub.quizList.R

enum class Organization(@StringRes val title: Int) {
    SQUIZ(R.string.filter_id_squiz),
    QUIZ_PLEASE(R.string.filter_id_quiz_please),
    SHAKER_QUIZ(R.string.filter_id_shaker_quiz),
    RUDA_GAMES(R.string.filter_id_ruda_games)
}