package ru.quizHub.common

fun<Key, Value> MutableMap<Key, MutableList<Value>>.addOrAppend(key: Key, value: Value) {
    if (this.containsKey(key)) this[key]?.add(value) else this[key] = mutableListOf(value)
}