package com.github.spyunderscore04.omegaskyblock.event

import com.github.spyunderscore04.omegaskyblock.log
import net.minecraftforge.fml.common.eventhandler.Event

class SwitchedProfileEvent(
    newProfileName: String
) : Event() {
    init {
        log.info("Switched to profile $newProfileName")
    }
}
