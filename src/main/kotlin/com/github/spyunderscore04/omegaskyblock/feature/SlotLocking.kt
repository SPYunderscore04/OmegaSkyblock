package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.log
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object SlotLocking: Feature() {

    val lockedSlots = mutableSetOf<Int>(42)

    fun handleMouseClick(inventorySlot: Int, button: Int, mode: Int, ci: CallbackInfo) {
        if (!isEnabled) return

        log.info("Clicked slot $inventorySlot with button $button and mode $mode")

        if (inventorySlot in lockedSlots) {
            log.info("Slot $inventorySlot is locked, cancelling click")
            ci.cancel()
        }
    }
}
