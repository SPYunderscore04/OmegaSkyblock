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

    private val jsonInstance = getConfiguredJsonInstance()
    private val serializationStrategy = OmegaSkyblockOptions.serializer()


    init {
        try {
            loadFromFile()
        } catch (e: IOException) {
            createNew()
        } catch (e: SerializationException) {
            createNew()
        }
    }

    fun loadFromFile() {
        log.info("Loading config from ${configFile.path}")

        val jsonText = configFile.readText()
        options = jsonInstance.decodeFromString(serializationStrategy, jsonText)
    }

    fun writeToFile() {
        log.trace("Writing config to ${configFile.path}")

        val json = jsonInstance.encodeToString(serializationStrategy, options)
        configFile.writeText(json)
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

    private fun createBackupFile() {
        val backupFile = File("${configFile.path}.old")

        log.info("Creating backup config at ${backupFile.path}")
        configFile.renameTo(backupFile)
    }

    private fun getConfiguredJsonInstance() = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        coerceInputValues = true
        allowSpecialFloatingPointValues = true
    }
}
