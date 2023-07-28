package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.events.SkyblockJoinEvent
import com.github.spyunderscore04.omegaskyblock.events.SkyblockLeaveEvent
import com.github.spyunderscore04.omegaskyblock.utils.unformatted
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent
import java.util.concurrent.atomic.AtomicBoolean

object Player {
    private var playsSkyblock = false
        set(value) {
            // fire events if necessary
            when {
                !field && value -> MinecraftForge.EVENT_BUS.post(SkyblockJoinEvent())
                field && !value -> MinecraftForge.EVENT_BUS.post(SkyblockLeaveEvent())
            }
            field = value
        }

    private var skyblockCheckInProgress = AtomicBoolean(false)

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        // check if player plays Skyblock
        Thread { checkPlaysSkyblock() }.start()
    }

    @SubscribeEvent
    fun onLeaveServer(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {
        // definitely leave Skyblock
        playsSkyblock = false
    }

    private fun checkPlaysSkyblock() {
        // only one check running at a time
        if (!skyblockCheckInProgress.compareAndSet(false, true)) return

        try {
            // await info
            var checked = false
            while (!checked) Minecraft.getMinecraft().theWorld
                ?.scoreboard
                ?.getObjectiveInDisplaySlot(1)
                ?.displayName
                ?.let {
                    playsSkyblock = it
                        .unformatted()
                        .contains("skyblock", true)
                    checked = true
                }
                ?: Thread.sleep(100)

        } finally {
            skyblockCheckInProgress.set(false)
        }
    }
}
