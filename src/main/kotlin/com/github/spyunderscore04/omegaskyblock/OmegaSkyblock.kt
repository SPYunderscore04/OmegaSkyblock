package com.github.spyunderscore04.omegaskyblock

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val log: Logger = LogManager.getLogger(OmegaSkyblock::class.java)

@Mod(modid = "omegaskyblock", useMetadata = true)
class OmegaSkyblock {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        log.info("Initialising OmegaSkyblock")

        // Register event listeners
        listOf(
            Player
        ).forEach(MinecraftForge.EVENT_BUS::register)
    }
}
