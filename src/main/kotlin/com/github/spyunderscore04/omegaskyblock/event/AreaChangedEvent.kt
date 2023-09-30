package com.github.spyunderscore04.omegaskyblock.event

import com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock.Area
import net.minecraftforge.fml.common.eventhandler.Event

class AreaChangedEvent(val newArea: Area): Event()
