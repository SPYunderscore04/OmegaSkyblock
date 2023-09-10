package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.util.KeyBinding

abstract class Feature {

    var isEnabled: Boolean = false
        set(value) {
            when {
                !field && value -> onEnable()
                field && !value -> onDisable()
            }
            field = value
        }

    open val keyBindings: Set<KeyBinding> = emptySet()

    protected open fun onEnable() {}

    protected open fun onDisable() {}

    protected fun runIfEnabled(block: () -> Unit) {
        if (isEnabled) block()
    }
}
