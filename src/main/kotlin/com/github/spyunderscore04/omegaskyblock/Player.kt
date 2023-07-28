package com.github.spyunderscore04.omegaskyblock

import com.github.spyunderscore04.omegaskyblock.events.SkyblockJoinEvent
import com.github.spyunderscore04.omegaskyblock.events.SkyblockLeaveEvent
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object Player {
    private var playsSkyblock = false
    private var skyblockCheckInProgress = false

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        // check if player plays Skyblock
        Thread { checkPlaysSkyblock() }.start()
    }

    private fun checkPlaysSkyblock() {
        // only one check running at a time
        if (skyblockCheckInProgress) return
        skyblockCheckInProgress = true

        val playedSkyblock = playsSkyblock

        // await info
        while (skyblockCheckInProgress) Minecraft.getMinecraft().theWorld
            ?.scoreboard
            ?.getObjectiveInDisplaySlot(1)
            ?.displayName?.let {
                // perform check
                playsSkyblock = it.contains("skyblock", true)
                skyblockCheckInProgress = false
            }
            ?: Thread.sleep(100)

        when {
            !playedSkyblock && playsSkyblock -> MinecraftForge.EVENT_BUS.post(SkyblockJoinEvent())
            playedSkyblock && !playsSkyblock -> MinecraftForge.EVENT_BUS.post(SkyblockLeaveEvent())
        }
    }
}
