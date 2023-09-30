package com.github.spyunderscore04.omegaskyblock.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.ThreadFactory
import kotlin.coroutines.CoroutineContext

object WorkerScope : CoroutineScope {

    override val coroutineContext: CoroutineContext

    init {
        val threadName = "OSB Worker"
        val threadFactory = ThreadFactory { runnable -> Thread(runnable, threadName) }
        val dispatcher = newSingleThreadExecutor(threadFactory).asCoroutineDispatcher()
        val supervisor = SupervisorJob()

        coroutineContext = dispatcher + supervisor
    }
}
