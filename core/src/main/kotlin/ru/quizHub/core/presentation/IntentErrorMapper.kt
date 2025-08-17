package ru.quizHub.core.presentation

import ru.quizHub.core.R
import ru.quizHub.core.presentation.model.IntentError
import ru.quizHub.core.resourceManager.IResourceManager

class IntentErrorMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapErrorMessage(error: IntentError): String = when (error) {
        IntentError.ActivityNotFound -> resourceManager.getStringById(R.string.error_activity_not_found)
        IntentError.IllegalArgument -> resourceManager.getStringById(R.string.error_illegal_argument)
        IntentError.Security -> resourceManager.getStringById(R.string.error_security)
        IntentError.GeoLocationError -> resourceManager.getStringById(R.string.error_no_such_element)
        is IntentError.Unknown -> resourceManager.getStringById(R.string.error_unknown)
    }
}