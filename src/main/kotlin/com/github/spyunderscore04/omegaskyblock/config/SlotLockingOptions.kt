package com.github.spyunderscore04.omegaskyblock.config

import kotlinx.serialization.Serializable

@Serializable
data class SlotLockingOptions(
    val lockedSlots: MutableSet<Int> = mutableSetOf(42)
)
