package com.github.spyunderscore04.omegaskyblock.feature

abstract class Feature {

    var isEnabled: Boolean = false
        set(value) {
            when {
                !field && value -> onEnable()
                field && !value -> onDisable()
            }
            field = value
        }

    protected open fun onEnable() {}

    protected open fun onDisable() {}
}
