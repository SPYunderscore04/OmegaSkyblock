package com.github.spyunderscore04.omegaskyblock.config

import com.github.spyunderscore04.omegaskyblock.log
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class Config(
    private val configFile: File
) {

    lateinit var options: OmegaSkyblockOptions
        private set

    private val serializer
        get() = OmegaSkyblockOptions.serializer()

    init {
        try {
            loadFromFile()
        } catch (e: Exception) {
            when (e) {
                is IOException,
                is SerializationException -> createNew()

                else -> throw e
            }
        }
    }

    fun loadFromFile() {
        log.info("Loading config from ${configFile.path}")

        val json = configFile.readText()
        options = Json.decodeFromString(serializer, json)
    }

    fun writeToFile() {
        val json = Json.encodeToString(serializer, options)
        configFile.writeText(json)
    }

    private fun createBackupFile() {
        val backupFile = File(configFile.path + ".old")

        log.info("Creating backup config at ${backupFile.path}")
        configFile.renameTo(backupFile)
    }

    private fun createNew() {
        if (configFile.exists()) {
            log.warn("Config will be overwritten!")
            createBackupFile()
        } else {
            log.info("Creating new config at ${configFile.path}")
            configFile.parentFile.mkdirs()
        }

        configFile.createNewFile()
        options = OmegaSkyblockOptions()
        writeToFile()
    }
}
