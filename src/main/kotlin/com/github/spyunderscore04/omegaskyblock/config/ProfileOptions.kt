package com.github.spyunderscore04.omegaskyblock.config

import kotlinx.serialization.Serializable

@Serializable
data class ProfileOptions(
    val lockedSlots: MutableSet<Int> = mutableSetOf()
)
