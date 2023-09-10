package com.github.spyunderscore04.omegaskyblock.util

fun String.unformatted() = replace(Regex("§[0-9a-fklmnor]"), "")

fun String.inSnakeCase() = replace(Regex("([a-z])([A-Z]+)"), "$1_$2").lowercase()
