package ru.dansh1nv.quiz.list.models.item

import androidx.annotation.StringRes
import ru.dansh1nv.designsystem.theme.status.StatusTag
import ru.dansh1nv.quiz.list.R

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