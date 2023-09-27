package com.github.spyunderscore04.omegaskyblock.util

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

suspend fun <T> awaitNotNull(block: () -> T?): T {
    var result: T? = null

    while (result == null)
        try {
            result = block()!!
        } catch (e: NullPointerException) {
            delay(100.milliseconds)
        }

    return result
}
