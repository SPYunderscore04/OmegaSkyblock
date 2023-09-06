package com.github.spyunderscore04.omegaskyblock.util

fun String.unformatted() = replace(Regex("§[0-9a-fklmnor]"), "")
