package com.github.spyunderscore04.omegaskyblock.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

object WorkerThread: CoroutineScope by CoroutineScope(
    Executors.newSingleThreadExecutor().asCoroutineDispatcher()
)
