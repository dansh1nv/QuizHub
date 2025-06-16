package ru.dansh1nv.common

private const val ONLINE_PLACE = "Онлайн"

fun Long?.orZero() = this.takeIf { it != 0L } ?: 0L
fun Int?.orZero() = this.takeIf { it != 0 } ?: 0

fun Boolean?.orTrue() = this.takeIf { it != null } ?: true
fun Boolean?.orFalse() = this.takeIf { it != null } ?: false

fun String.isPhysicalPlace(onlinePlace: String = ONLINE_PLACE) = this != onlinePlace