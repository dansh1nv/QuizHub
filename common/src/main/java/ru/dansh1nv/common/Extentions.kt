package ru.dansh1nv.common

fun Long?.orZero() = this.takeIf { it != 0L } ?: 0L