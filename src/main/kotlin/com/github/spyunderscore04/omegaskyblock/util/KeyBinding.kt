package com.github.spyunderscore04.omegaskyblock.util

import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraft.client.settings.KeyBinding as McKeyBinding

class KeyBinding(keyCode: Int, i18nKey: I18nKey) {

    private val mcKeyBinding = McKeyBinding(
        i18nKey.string,
        keyCode,
        categoryName
    )

    fun register() = ClientRegistry.registerKeyBinding(mcKeyBinding)

    companion object {
        private val categoryName: String = I18nKey.of(KeyBinding::class, ::categoryName).string
    }
}
