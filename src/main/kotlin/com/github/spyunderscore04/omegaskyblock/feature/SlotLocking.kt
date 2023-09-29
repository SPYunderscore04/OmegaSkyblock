package com.github.spyunderscore04.omegaskyblock.feature

import com.github.spyunderscore04.omegaskyblock.gamemodel.SlotClickType
import com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock.CurrentProfile
import com.github.spyunderscore04.omegaskyblock.util.I18nKey
import com.github.spyunderscore04.omegaskyblock.util.KeyBinding
import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.Slot
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object SlotLocking : Feature() {

    private const val SWAPPED_SLOT_SAFETY_MS = 150

    private val toggleLockKey: KeyBinding = KeyBinding(Keyboard.KEY_L, I18nKey.of(this::class, ::toggleLockKey))

    private var lastSwappedFromLockedSlotTimestamp = 0L
    private var lastHoveredSlot: Slot? = null

    init {
        toggleLockKey.register()
    }

    fun handleMouseClick(slot: Slot, buttonId: Int, clickType: SlotClickType, ci: CallbackInfo) = runIfEnabled {
        if (slot.inventory != Minecraft.getMinecraft().thePlayer.inventory) return@runIfEnabled

        if (clickHasLockViolation(slot, buttonId, clickType))
            ci.cancel()
    }

    fun handleDropItem(cir: CallbackInfoReturnable<EntityItem>) = runIfEnabled {
        val slotIndex = Minecraft.getMinecraft().thePlayer.inventory.currentItem

        if (isSlotLocked(slotIndex) || recentlySwappedFromLockedSlot())
            cir.cancel()
    }

    fun handleCurrentItemChanged() = runIfEnabled {
        val now = System.currentTimeMillis()
        val slotIndex = Minecraft.getMinecraft().thePlayer.inventory.currentItem

        if (isSlotLocked(slotIndex)) lastSwappedFromLockedSlotTimestamp = now
    }

    fun handleHoveringSlot(slot: Slot?) = runIfEnabled {
        lastHoveredSlot = slot
    }

    fun handleKeyTyped(keyCode: Int) = runIfEnabled {
        if (keyCode == toggleLockKey.keyCode) lastHoveredSlot?.let(::toggleLock)
    }

    private fun toggleLock(slot: Slot) = CurrentProfile.options?.lockedSlots?.run {
        if (isSlotLocked(slot.slotIndex))
            remove(slot.slotIndex)
        else
            add(slot.slotIndex)
    }

    private fun clickHasLockViolation(slot: Slot, buttonId: Int, clickType: SlotClickType): Boolean {
        if (isSlotLocked(slot.slotIndex)) return true
        if (clickType == SlotClickType.HOTKEY && isSlotLocked(buttonId)) return true
        return false
    }

    private fun isSlotLocked(slotIndex: Int) = CurrentProfile.options?.lockedSlots?.contains(slotIndex) == true

    private fun recentlySwappedFromLockedSlot(): Boolean {
        return lastSwappedFromLockedSlotTimestamp + SWAPPED_SLOT_SAFETY_MS > System.currentTimeMillis()
    }
}
