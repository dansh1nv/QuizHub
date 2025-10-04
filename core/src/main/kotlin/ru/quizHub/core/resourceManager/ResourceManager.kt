package ru.quizHub.core.resourceManager

import android.content.Context

class ResourceManager(private val context: Context) : IResourceManager {

    override fun getStringById(resId: Int): String {
        return context.getString(resId)
    }

    override fun getStringById(resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }

    override fun getJson(name: String): String {
        return try {
            context.assets.open(name).bufferedReader().use { it.readText() }
        } catch (ex: Throwable) {
            ""
        }
    }
}