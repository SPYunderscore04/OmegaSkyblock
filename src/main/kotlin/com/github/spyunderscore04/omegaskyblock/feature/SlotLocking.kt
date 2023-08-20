package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.OmegaSkyblock
import com.github.spyunderscore04.omegaskyblock.log
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object SlotLocking: Feature() {

    fun handleMouseClick(inventorySlot: Int, button: Int, mode: Int, ci: CallbackInfo) {
        if (!isEnabled) return

        log.info("Clicked slot $inventorySlot with button $button and mode $mode")

        if (inventorySlot in OmegaSkyblock.options.slotLocking.lockedSlots) {
            log.info("Slot $inventorySlot is locked, cancelling click")
            ci.cancel()
        }
    }
}
