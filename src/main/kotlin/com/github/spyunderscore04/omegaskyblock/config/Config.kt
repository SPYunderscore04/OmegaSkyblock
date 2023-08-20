package com.github.spyunderscore04.omegaskyblock.config

import com.github.spyunderscore04.omegaskyblock.log
import com.github.spyunderscore04.omegaskyblock.utils.WorkerScope
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import kotlin.time.Duration.Companion.minutes

class Config(
    private val configFile: File
) {

    private val fileMutex = Mutex()
    private val jsonInstance = getConfiguredJsonInstance()
    private val serializationStrategy = OmegaSkyblockOptions.serializer()

    private var oldOptionsHash: Int

    val options: OmegaSkyblockOptions = getInitialOptions()

    init {
        oldOptionsHash = options.hashCode()
        runBlocking { writeToFile() } // ensures that writing works, and that options added in a newer version are saved

        WorkerScope.launch { keepFileUpdated(scope = this) }
    }

    private fun getConfiguredJsonInstance() = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        coerceInputValues = true
        allowSpecialFloatingPointValues = true
    }

    private fun getInitialOptions(): OmegaSkyblockOptions = runBlocking {
        try {
            return@runBlocking getFromFile()
        } catch (e: Exception) {
            if (e !is IOException && e !is SerializationException) throw e

            log.warn("Failed to load config from ${configFile.path}: ${e.message}")
            createNewEmptyFile()
            return@runBlocking OmegaSkyblockOptions()
        }
    }

    private suspend fun getFromFile(): OmegaSkyblockOptions = fileMutex.withLock {
        log.info("Loading config from ${configFile.path}")

        val jsonText = configFile.readText()
        return jsonInstance.decodeFromString(serializationStrategy, jsonText)
    }

    private suspend fun createNewEmptyFile() = fileMutex.withLock {
        if (configFile.exists()) {
            log.warn("Replacing config file")
            renameFileToBackup()
        } else {
            log.info("Creating new config at ${configFile.path}")
            configFile.parentFile.mkdirs()
        }

        configFile.createNewFile()
    }

    private fun renameFileToBackup() {
        val backupFile = File("${configFile.path}.old")

        log.info("Creating backup config at ${backupFile.path}")
        configFile.renameTo(backupFile)
    }

    private suspend fun keepFileUpdated(scope: CoroutineScope) {
        while (scope.isActive) {
            val currentHash = options.hashCode()
            if (currentHash != oldOptionsHash) {
                oldOptionsHash = currentHash
                writeToFile()
            }

            delay(1.minutes)
        }
    }

    private suspend fun writeToFile() = fileMutex.withLock {
        log.info("Writing config to ${configFile.path}")

        val json = jsonInstance.encodeToString(serializationStrategy, options)
        configFile.writeText(json)
    }
}
