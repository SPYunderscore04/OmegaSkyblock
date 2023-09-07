package com.github.spyunderscore04.omegaskyblock.mixin;

import com.github.spyunderscore04.omegaskyblock.feature.SlotLocking;
import org.objectweb.asm.Opcodes;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = Opcodes.PUTFIELD))
    private void onCurrentItemChanged(CallbackInfo ci) {
        SlotLocking.INSTANCE.handleCurrentItemChanged();
    }
}
