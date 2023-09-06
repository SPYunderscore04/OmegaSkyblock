package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.OmegaSkyblock
import com.github.spyunderscore04.omegaskyblock.log
import net.minecraft.client.Minecraft
import net.minecraft.inventory.Slot
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object SlotLocking : Feature() {

    fun handleMouseClick(slot: Slot?, button: Int, mode: Int, ci: CallbackInfo) = runIfEnabled {
        if (slot == null) return@runIfEnabled
        if (slot.inventory != Minecraft.getMinecraft().thePlayer.inventory) return@runIfEnabled

        if (slot.slotIndex in OmegaSkyblock.options.slotLocking.lockedSlots) {
            log.info("Prevented click on locked slot ${slot.slotIndex}")
            ci.cancel()
        }
    }
}
