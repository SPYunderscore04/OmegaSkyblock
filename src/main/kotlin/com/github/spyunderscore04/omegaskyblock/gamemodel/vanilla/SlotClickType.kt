package com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla

enum class SlotClickType(val id: Int) {
    REGULAR(0),
    SHIFT(1),
    HOTKEY(2),
    CREATIVE_DUPLICATION(3),
    DROP(4),
    DISTRIBUTE(5),
    DOUBLECLICK(6);

    companion object {
        fun fromInt(id: Int) = SlotClickType.entries.first { it.id == id }
    }
}