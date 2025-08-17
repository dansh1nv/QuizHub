package ru.quizHub.core.resourceManager

import androidx.annotation.StringRes

interface IResourceManager {

    fun getStringById(@StringRes resId: Int): String

    fun getStringById(@StringRes resId: Int, vararg args: String): String

    fun getJson(name: String): String

}