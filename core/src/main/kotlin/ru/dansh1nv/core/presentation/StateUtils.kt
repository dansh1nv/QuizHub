package ru.dansh1nv.core.presentation

inline fun <T> TriState<T>.onLoading(block: () -> Unit): TriState<T> {
    if (this is TriState.Loading) {
        block()
    }
    return this
}

inline fun <T> TriState<T>.onLoaded(block: () -> Unit): TriState<T> {
    if (this is TriState.Loaded) {
        block()
    }
    return this
}

inline fun <T> TriState<T>.onError(block: () -> Unit): TriState<T> {
    if (this is TriState.Error) {
        block()
    }
    return this
}