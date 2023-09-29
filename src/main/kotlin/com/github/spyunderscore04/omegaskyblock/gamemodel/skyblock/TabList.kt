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
        val lines = contents.mapNotNull { it.displayName?.firstOrNull()?.unformattedText }
        val dataGroups = parseLines(lines)

        val data = TabListData()
        dataGroups["Account Info"]?.runCatching {
            data.accountInfo = AccountInfo.fromTabListLines(this)
        }

        return data
    }

    private fun parseLines(lines: List<String>): Map<String, List<String>> {
        val columnSeparatedLines = lines
            .chunked(20)
            .flatMap { it + "" }

        val emptyLineIndices = columnSeparatedLines
            .mapIndexedNotNull { index, line ->
                if (line.isBlank()) index
                else null
            }

        val groupDelimiters = (listOf(-1) + emptyLineIndices)
            .zipWithNext()

        return groupDelimiters
            .map { (start, end) -> columnSeparatedLines.subList(start + 1, end) }
            .filter { it.isNotEmpty() }
            .map {
                val title = it.first().trim()
                val content = it.drop(1)
                title to content
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.flatten() }
    }
}
