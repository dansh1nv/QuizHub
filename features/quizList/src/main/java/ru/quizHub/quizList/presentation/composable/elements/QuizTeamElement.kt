package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.IconCell
import ru.quizHub.designsystem.theme.elements.TextCell
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.item.TeamSizeUI

@Composable
internal fun QuizTeamElement(
    teamSizeUI: TeamSizeUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconCell(iconRes = R.drawable.ic_team)
        TextCell(text = teamSizeUI.teamSizeText)
    }
}