package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.events.SkyblockJoinEvent
import com.github.spyunderscore04.omegaskyblock.events.SkyblockLeaveEvent
import com.github.spyunderscore04.omegaskyblock.utils.WorkerScope
import com.github.spyunderscore04.omegaskyblock.utils.unformatted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeout
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object World {
    private val skyblockCheckMutex = Mutex()

    private var isSkyblock = false
        set(value) {
            when {
                !field && value -> MinecraftForge.EVENT_BUS.post(SkyblockJoinEvent())
                field && !value -> MinecraftForge.EVENT_BUS.post(SkyblockLeaveEvent())
            }
            field = value
        }

    @SubscribeEvent
    fun onLoad(event: WorldEvent.Load) = WorkerScope.launch {
        checkIsSkyblock()
    }

    @SubscribeEvent
    fun onDisconnect(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) = WorkerScope.launch {
        // definitely no longer Skyblock
        isSkyblock = false
    }

    private suspend fun checkIsSkyblock() {
        if (skyblockCheckMutex.tryLock()) try {
            val sidebarTitle = withTimeout(5000) { getSidebarTitle() }
            isSkyblock = sidebarTitle.unformatted().contains("skyblock", ignoreCase = true)
        } finally {
            skyblockCheckMutex.unlock()
        }
    }

    private suspend fun getSidebarTitle(): String {
        // TODO cleanup infinite loop?
        while (true) Minecraft.getMinecraft().theWorld
            ?.scoreboard
            ?.getObjectiveInDisplaySlot(1)
            ?.displayName
            ?.let { return it }
            ?: delay(100)
    }
}
