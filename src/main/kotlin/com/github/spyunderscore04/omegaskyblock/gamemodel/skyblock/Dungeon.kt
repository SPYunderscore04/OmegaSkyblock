package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

import com.github.spyunderscore04.omegaskyblock.event.AreaChangedEvent
import com.github.spyunderscore04.omegaskyblock.event.SidebarChangedEvent
import com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla.Sidebar
import com.github.spyunderscore04.omegaskyblock.util.WorkerScope
import kotlinx.coroutines.launch
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object Dungeon {

    var phase: DungeonPhase? = null
        private set
    private var timeElapsed = (-1).seconds

    @SubscribeEvent
    fun onAreaChanged(event: AreaChangedEvent) = WorkerScope.launch {
        when {
            event.newArea == Area.THE_CATACOMBS -> {
                phase = DungeonPhase.PREPARATION
                timeElapsed = (-1).seconds
            }

            phase != null -> {
                phase = null
            }
        }
    }

    @SubscribeEvent
    fun onSidebarChanged(event: SidebarChangedEvent) = WorkerScope.launch {
        phase?.let {
            when (it) {
                DungeonPhase.PREPARATION -> whenPreparing()
                DungeonPhase.CLEAR -> whenClearing()
//            DungeonPhase.BOSS_FIGHT -> whenFightingBoss()
//            DungeonPhase.DONE -> whenDone()
            }
        }
    }

    private fun whenPreparing() {
        Sidebar.getValue("Time Elapsed: ")?.let {
            phase = DungeonPhase.CLEAR
            timeElapsed = parseTime(it)
        }
    }

    private fun whenClearing() {
        Sidebar.getValue("Time Elapsed: ")?.let {
            timeElapsed = parseTime(it)
        }
    }

    private fun parseTime(timeString: String): Duration {
        val minutes = timeString
            .substringBefore('m')
            .toIntOrNull() ?: 0
        val seconds = timeString
            .substringAfter('m')
            .substringBefore('s')
            .toIntOrNull() ?: 0
        return minutes.minutes + seconds.seconds
    }
}
