package com.github.spyunderscore04.omegaskyblock.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * Convenience object to always have a [CoroutineScope] ready for running things in parallel
 *
 * Especially useful to speed up event processing:
 * ```
 *     @SubscribeEvent
 *     fun onEvent(event: Event) = WorkerThread.launch {
 *         // do some heavy lifting
 *     }
 * ```
 */
object WorkerThread: CoroutineScope by CoroutineScope(
    Executors.newSingleThreadExecutor { Thread(it, "OSB Worker") }.asCoroutineDispatcher()
)
