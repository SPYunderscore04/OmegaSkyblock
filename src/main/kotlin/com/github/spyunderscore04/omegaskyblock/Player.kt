package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.events.SkyblockJoinEvent
import com.github.spyunderscore04.omegaskyblock.events.SkyblockLeaveEvent
import com.github.spyunderscore04.omegaskyblock.utils.WorkerThread
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

object Player {
    private val skyblockCheckMutex = Mutex()

    private var playsSkyblock = false
        set(value) {
            // fire events if necessary
            when {
                !field && value -> MinecraftForge.EVENT_BUS.post(SkyblockJoinEvent())
                field && !value -> MinecraftForge.EVENT_BUS.post(SkyblockLeaveEvent())
            }
            field = value
        }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) = WorkerThread.launch {
        checkPlaysSkyblock()
    }

    @SubscribeEvent
    fun onLeaveServer(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) = WorkerThread.launch {
        // definitely leave Skyblock
        playsSkyblock = false
    }

    private suspend fun checkPlaysSkyblock() {
        if (skyblockCheckMutex.tryLock()) try {
            withTimeout(5000) {
                // await info
                var checked = false
                while (!checked) Minecraft.getMinecraft().theWorld
                    ?.scoreboard
                    ?.getObjectiveInDisplaySlot(1)
                    ?.displayName
                    ?.let {
                        playsSkyblock = it.unformatted().contains("skyblock", true)
                        checked = true
                    }
                    ?: delay(100)
            }
        } finally {
            skyblockCheckMutex.unlock()
        }
    }
}
