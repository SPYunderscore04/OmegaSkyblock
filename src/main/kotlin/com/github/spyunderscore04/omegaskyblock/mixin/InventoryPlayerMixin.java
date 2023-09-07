package com.github.spyunderscore04.omegaskyblock.mixin;

import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryPlayer.class)
public class InventoryPlayerMixin {

    @Inject(method = "changeCurrentItem", at = @At("HEAD"))
    public void beforeChangeCurrentItem(int direction, CallbackInfo ci) {
        SlotLocking.INSTANCE.handleCurrentItemChanged();
    }
}
