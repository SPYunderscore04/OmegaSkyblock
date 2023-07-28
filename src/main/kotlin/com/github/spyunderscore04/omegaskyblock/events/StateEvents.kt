package com.github.spyunderscore04.omegaskyblock.events

import com.github.spyunderscore04.omegaskyblock.log
import net.minecraftforge.fml.common.eventhandler.Event

class SkyblockJoinEvent : Event() {
    init {
        log.info("Joined Skyblock!")
    }
}

class SkyblockLeaveEvent : Event() {
    init {
        log.info("Left Skyblock!")
    }
}
