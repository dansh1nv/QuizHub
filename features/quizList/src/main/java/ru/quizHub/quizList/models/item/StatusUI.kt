package ru.quizHub.quizList.models.item

import androidx.annotation.StringRes
import ru.quizHub.designsystem.theme.status.StatusTag
import ru.quizHub.quizList.R

enum class StatusUI(
    @StringRes
    val titleRes: Int,
    val tag: StatusTag,
) {
    WRITE_TO_GAME(
        titleRes = R.string.quiz_status_open,
        tag = StatusTag.REGISTRATION_OPENED
    ),
    WRITE_TO_RESERVE(
        titleRes = R.string.quiz_status_reserve,
        tag = StatusTag.RESERVATION
    ),
    RESERVATION_CLOSE(
        titleRes = R.string.quiz_status_close,
        tag = StatusTag.REGISTRATION_CLOSED
    );
}