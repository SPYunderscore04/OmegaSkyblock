package com.github.spyunderscore04.omegaskyblock.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.ThreadFactory
import kotlin.coroutines.CoroutineContext


const val THREAD_NAME = "OSB Worker"

object WorkerScope : CoroutineScope {

    override val coroutineContext: CoroutineContext

    init {
        val threadFactory = ThreadFactory { runnable -> Thread(runnable, THREAD_NAME) }
        val dispatcher = newSingleThreadExecutor(threadFactory).asCoroutineDispatcher()
        val supervisor = SupervisorJob()

        coroutineContext = dispatcher + supervisor
    }
}
