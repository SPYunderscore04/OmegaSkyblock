package com.github.spyunderscore04.omegaskyblock.mixin;

import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    public void beforeDropOneItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        SlotLocking.INSTANCE.handleDropItem(cir);
    }
}
