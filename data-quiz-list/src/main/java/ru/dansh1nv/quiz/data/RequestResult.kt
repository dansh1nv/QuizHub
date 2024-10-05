package ru.dansh1nv.quiz.data

sealed class RequestResult<E>(internal val data: E) {

    class InProgress<E>(data: E) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)
    class Error<E>(data: E) : RequestResult<E>(data)

}