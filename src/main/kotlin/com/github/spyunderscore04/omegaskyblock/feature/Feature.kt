package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.event.JoinedSkyblockEvent
import com.github.spyunderscore04.omegaskyblock.event.LeftSkyblockEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

abstract class Feature {

    private var isEnabled: Boolean = false
        set(value) {
            when {
                !field && value -> onEnable()
                field && !value -> onDisable()
            }
            field = value
        }

    @SubscribeEvent
    fun onJoinedSkyblock(event: JoinedSkyblockEvent) {
        isEnabled = true
    }

    @SubscribeEvent
    fun onLeftSkyblock(event: LeftSkyblockEvent) {
        isEnabled = false
    }

    protected open fun onEnable() {}

    protected open fun onDisable() {}

    protected fun runIfEnabled(block: () -> Unit) {
        if (isEnabled) block()
    }
}
