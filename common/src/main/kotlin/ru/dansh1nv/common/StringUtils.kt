package ru.dansh1nv.common

fun formatStringsWithDividerPoints(
    strings: Array<String?>,
    divider: StringDividerType
): String = strings
    .filterNot { it.isNullOrEmpty() }
    .joinToString(divider.value)