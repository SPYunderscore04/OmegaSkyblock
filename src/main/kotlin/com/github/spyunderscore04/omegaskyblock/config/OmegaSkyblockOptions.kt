package com.github.spyunderscore04.omegaskyblock.config

import kotlinx.serialization.Serializable

@Serializable
data class OmegaSkyblockOptions(
    val slotLocking: SlotLockingOptions = SlotLockingOptions()
)
