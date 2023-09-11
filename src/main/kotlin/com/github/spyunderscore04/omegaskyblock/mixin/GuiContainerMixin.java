package com.github.spyunderscore04.omegaskyblock.mixin;

import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking;
import com.github.spyunderscore04.omegaskyblock.gamemodel.SlotClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GuiContainer.class)
public class GuiContainerMixin {

    @Shadow
    private Slot getSlotAtPosition(int x, int y) {
        return null;
    }

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void beforeDrawScreen(int mouseX, int mouseY, float partialTicks, @NotNull CallbackInfo ci) {

        Slot slot = getSlotAtPosition(mouseX, mouseY);
        SlotLocking.INSTANCE.handleHoveringSlot(slot);
    }

    @Inject(method = "handleMouseClick", at = @At("HEAD"), cancellable = true)
    public void beforeHandleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, @NotNull CallbackInfo ci) {
        if (slotIn == null) return;

        SlotClickType clickTypeEnum = SlotClickType.Companion.fromInt(clickType);

        SlotLocking.INSTANCE.handleMouseClick(slotIn, clickedButton, clickTypeEnum, ci);
    }

    @Inject(method = "keyTyped", at = @At("HEAD"))
    public void beforeKeyTyped(char typedChar, int keyCode, @NotNull CallbackInfo ci) {
        SlotLocking.INSTANCE.handleKeyTyped(keyCode);
    }
}
