package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.OmegaSkyblock
import com.github.spyunderscore04.omegaskyblock.gamemodel.SlotClickType
import com.github.spyunderscore04.omegaskyblock.log
import net.minecraft.client.Minecraft
import net.minecraft.inventory.Slot
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object SlotLocking : Feature() {

    fun handleMouseClick(slot: Slot, buttonId: Int, clickType: SlotClickType, ci: CallbackInfo) = runIfEnabled {
        if (slot.inventory != Minecraft.getMinecraft().thePlayer.inventory) return@runIfEnabled

        val clickViolatesLock = when (clickType) {
            SlotClickType.HOTKEY ->
                isSlotLocked(slot.slotIndex) || isSlotLocked(buttonId)

            else ->
                isSlotLocked(slot.slotIndex)
        }

        log.info("Click on slot ${slot.slotIndex}, button $buttonId, clickType $clickType violates lock: $clickViolatesLock")
        if (clickViolatesLock) ci.cancel()
    }

    private fun isSlotLocked(slotIndex: Int) = slotIndex in OmegaSkyblock.options.slotLocking.lockedSlots
}
