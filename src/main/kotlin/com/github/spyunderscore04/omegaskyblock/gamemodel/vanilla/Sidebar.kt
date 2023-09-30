package com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla

import com.github.spyunderscore04.omegaskyblock.event.SidebarChangedEvent
import com.github.spyunderscore04.omegaskyblock.util.WorkerScope
import com.github.spyunderscore04.omegaskyblock.util.unformatted
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.ScoreObjective
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Sidebar {
    var title: String? = null
        private set
    var lines: List<String> = emptyList()
        private set

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END)
            WorkerScope.launch { updateValues() }
    }

    fun getValue(key: String): String? {
        return lines
            .firstOrNull { it.startsWith(key) }
            ?.substringAfter(key)
    }

    private fun updateValues() {
        var currentTitle: String? = null
        var currentLines = emptyList<String>()

        Minecraft.getMinecraft().theWorld
            ?.scoreboard
            ?.getObjectiveInDisplaySlot(1)
            ?.let {
                currentTitle = it.displayName.unformatted()
                currentLines = getSidebarLines(it)
            }

        if (title != currentTitle || lines != currentLines) {
            title = currentTitle
            lines = currentLines
            MinecraftForge.EVENT_BUS.post(SidebarChangedEvent())
        }
    }
}

private fun getSidebarLines(objective: ScoreObjective) = objective
    .scoreboard.getSortedScores(objective).reversed()
    .map {
        val team = objective.scoreboard.getPlayersTeam(it.playerName)
        (team.colorPrefix + team.colorSuffix).unformatted()
    }
