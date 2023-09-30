package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

import com.github.spyunderscore04.omegaskyblock.event.TabListChangedEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiPlayerTabOverlay
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraftforge.common.MinecraftForge

object TabList {

    var currentData = TabListData()
        private set(value) {
            if (field != value) {
                field = value
                MinecraftForge.EVENT_BUS.post(TabListChangedEvent())
            }
        }

    fun onPlayersChanged() {
        val contents = GuiPlayerTabOverlay.field_175252_a
            .sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.playerInfoMap)

        currentData = parseContents(contents)
    }

    private fun parseContents(contents: List<NetworkPlayerInfo>): TabListData {
        val lines = contents
            .mapNotNull { it.displayName?.firstOrNull()?.unformattedText }
            .filter { it.isNotBlank() }

        val data = TabListData()
        lines.getValue("Profile: ")?.let { data.profileName = it }

        return data
    }
}

private fun List<String>.getValue(key: String) = find { it.contains(key) }?.substringAfter(key)
