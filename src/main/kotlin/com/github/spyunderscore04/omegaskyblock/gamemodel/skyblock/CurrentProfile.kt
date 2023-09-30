package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

import com.github.spyunderscore04.omegaskyblock.OmegaSkyblock
import com.github.spyunderscore04.omegaskyblock.config.ProfileOptions
import com.github.spyunderscore04.omegaskyblock.event.SwitchedProfileEvent
import com.github.spyunderscore04.omegaskyblock.event.TabListChangedEvent
import com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla.TabList
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object CurrentProfile {
    var name: String? = null
        private set(value) {
            if (field != value) {
                onProfileNameChanged(value)
                field = value
            }
        }
    var options: ProfileOptions? = null
    val isRift: Boolean
        get() = name
            ?.let { it.last().isUpperCase() && it.first().isLowerCase() }
            ?: false

    @SubscribeEvent
    fun onTabListChanged(event: TabListChangedEvent) {
        name = TabList.currentData.profileName
    }

    private fun onProfileNameChanged(newName: String?) {
        newName?.let { name ->
            MinecraftForge.EVENT_BUS.post(SwitchedProfileEvent(name))
            options = OmegaSkyblock.options.profileOptions.getOrPut(name) { ProfileOptions() }
        } ?: run {
            options = null
        }
    }
}
