package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

import com.github.spyunderscore04.omegaskyblock.event.AreaChangedEvent
import com.github.spyunderscore04.omegaskyblock.event.SidebarChangedEvent
import com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla.Sidebar
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

private const val AREA_PREFIX = "⏣ "

enum class Area(val areaName: String) {
    THE_CATACOMBS("The Catacombs"),
    UNKNOWN("UNKNOWN");

    companion object {

        var current = UNKNOWN
            private set(value) {
                if (field != value) {
                    field = value
                    MinecraftForge.EVENT_BUS.post(AreaChangedEvent(value))
                }
            }

        @SubscribeEvent
        fun onSidebarChanged(event: SidebarChangedEvent) {
            current = getCurrentArea()
        }

        private fun getCurrentArea(): Area {
            val line = Sidebar.lines
                .firstOrNull { it.contains(AREA_PREFIX) }
                ?: return UNKNOWN

            val currentAreaName = line.substringAfter(AREA_PREFIX)
            return entries.firstOrNull { currentAreaName.startsWith(it.areaName) } ?: UNKNOWN
        }
    }
}
