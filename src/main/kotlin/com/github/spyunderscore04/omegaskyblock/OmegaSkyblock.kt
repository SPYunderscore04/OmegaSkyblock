package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.utils.WorkerScope
import kotlinx.coroutines.launch
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val log: Logger = LogManager.getLogger(OmegaSkyblock::class.java)

@Mod(modid = "omegaskyblock", useMetadata = true)
class OmegaSkyblock {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) = WorkerScope.launch {
        log.info("Pre-Init!")
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) = WorkerScope.launch {
        log.info("Init!")

        registerEventListeners()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) = WorkerScope.launch {
        log.info("Post-Init!")
    }

    private fun registerEventListeners() {
        listOf(
            World
        ).forEach(MinecraftForge.EVENT_BUS::register)
    }
}
