package ru.dansh1nv.core.presentation

import ru.dansh1nv.core.R
import ru.dansh1nv.core.presentation.model.IntentError
import ru.dansh1nv.core.resourceManager.IResourceManager

class IntentErrorMapper(
    private val resourceManager: IResourceManager,
) {
    fun map(error: IntentError): String = when (error) {
        IntentError.ActivityNotFound -> resourceManager.getStringById(R.string.error_activity_not_found)
        IntentError.IllegalArgument -> resourceManager.getStringById(R.string.error_illegal_argument)
        IntentError.Security -> resourceManager.getStringById(R.string.error_security)
        IntentError.NoSuchElementException -> resourceManager.getStringById(R.string.error_no_such_element)
        is IntentError.Unknown -> resourceManager.getStringById(R.string.error_unknown)
    }
}