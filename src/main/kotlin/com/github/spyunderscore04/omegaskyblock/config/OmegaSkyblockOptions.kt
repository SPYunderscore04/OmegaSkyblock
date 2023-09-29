package com.github.spyunderscore04.omegaskyblock.config

import kotlinx.serialization.Serializable

@Serializable
data class OmegaSkyblockOptions(
    val profileOptions: MutableMap<String, ProfileOptions> = mutableMapOf()
)
