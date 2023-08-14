package com.github.spyunderscore04.omegaskyblock.config

import kotlinx.serialization.Serializable

@Serializable
data class SlotLockingOptions(
    val lockedSlots: Set<Int> = setOf(42)
)
