package com.github.spyunderscore04.omegaskyblock.mixins;

import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GuiContainer.class)
public class GuiContainerMixin {

    @Inject(method = "handleMouseClick", at = @At("HEAD"), cancellable = true)
    public void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, @NotNull CallbackInfo ci) {
        /* Research
         * ========
         * slotId:
         *   0: crafting result
         *   1-4: crafting grid
         *   5-8: armor
         *   9-35: inventory
         *   36-44: hotbar
         *   -999: outside inventory
         *
         * clickedButton:
         *   if mouse:
         *     0: left mouse
         *     1: right mouse
         *     2: middle mouse
         *   if drop:
         *     0: regular
         *     1: stack
         *   if hotkey:
         *     0-8: hotbar slot
         *
         * clickType:
         *   0: regular
         *   1: shift
         *   2: hotkey
         *   3: duplicate (middle mouse)
         *   4: drop
         *   5: spread (multiple calls)
         *   6: double click (both other calls are still made)
         */

        SlotLocking.INSTANCE.handleMouseClick(slotIn, clickedButton, clickType, ci);
    }
}
