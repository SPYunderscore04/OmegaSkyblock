package com.github.spyunderscore04.omegaskyblock.event

import com.github.spyunderscore04.omegaskyblock.log
import net.minecraftforge.fml.common.eventhandler.Event

class JoinedSkyblockEvent : Event() {
    init {
        log.info("Joined Skyblock!")
    }
}
