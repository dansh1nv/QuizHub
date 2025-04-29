package ru.dansh1nv.core.presentation

sealed class TriState<T> {
    class Loading<T> : TriState<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    data class Loaded<T>(val model: T) : TriState<T>()
    data class Error<T>(
        val model: T? = null,
        val exception: Exception? = null,
        val exceptionMessage: String? = null
    ) : TriState<T>()
}