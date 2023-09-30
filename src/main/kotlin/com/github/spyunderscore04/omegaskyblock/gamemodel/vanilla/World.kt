package com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla

import com.github.spyunderscore04.omegaskyblock.event.JoinedSkyblockEvent
import com.github.spyunderscore04.omegaskyblock.event.LeftSkyblockEvent
import com.github.spyunderscore04.omegaskyblock.event.SidebarChangedEvent
import com.github.spyunderscore04.omegaskyblock.util.WorkerScope
import kotlinx.coroutines.launch
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object World {

    var isSkyblock = false
        set(value) {
            when {
                !field && value -> {
                    field = true
                    MinecraftForge.EVENT_BUS.post(JoinedSkyblockEvent())
                }

                field && !value -> {
                    field = false
                    MinecraftForge.EVENT_BUS.post(LeftSkyblockEvent())
                }
            }
        }

    @SubscribeEvent
    fun onSidebarChanged(event: SidebarChangedEvent) = WorkerScope.launch {
        isSkyblock = Sidebar.title?.isSkyblockTitle() ?: false
    }
}

private val SKYBLOCK_TITLES = arrayOf(
    "SKYBLOCK",
    "\u7A7A\u5C9B\u751F\u5B58",
    "\u7A7A\u5CF6\u751F\u5B58",
    "SKIBLOCK"
)

private fun String.isSkyblockTitle() = SKYBLOCK_TITLES.any { title -> this.contains(title, ignoreCase = true) }
