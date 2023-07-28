package com.github.spyunderscore04.omegaskyblock.utils

fun String.unformatted() = replace(Regex("§[0-9a-fklmnor]"), "")
