package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.config.Config
import com.github.spyunderscore04.omegaskyblock.config.OmegaSkyblockOptions
import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking
import com.github.spyunderscore04.omegaskyblock.gamemodel.World
import com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock.CurrentProfile
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

val log: Logger = LogManager.getLogger(OmegaSkyblock::class.java)

@Mod(modid = "omegaskyblock", useMetadata = true)
class OmegaSkyblock {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        log.info("Pre-Init!")

        loadConfig(event)
        registerEventListeners()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        log.info("Init!")

        initialiseFeatures()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        log.info("Post-Init!")
    }

    companion object {

        val modId = OmegaSkyblock::class.simpleName!!.lowercase()

        val options: OmegaSkyblockOptions
            get() = config.options

        private lateinit var config: Config

        private fun loadConfig(event: FMLPreInitializationEvent) {
            val ownConfigDir = File(
                event.modConfigurationDirectory.canonicalPath,
                modId
            )
            config = Config(File(ownConfigDir, "config.json"))
        }

        private fun registerEventListeners() {
            listOf(
                CurrentProfile,
                World
            ).forEach(MinecraftForge.EVENT_BUS::register)
        }

        private fun initialiseFeatures() {
            listOf(
                SlotLocking
            ).forEach { it.isEnabled = true }
        }
    }
}
