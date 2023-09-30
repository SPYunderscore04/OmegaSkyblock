package com.github.spyunderscore04.omegaskyblock.mixin;

import com.github.spyunderscore04.omegaskyblock.gamemodel.vanilla.TabList;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {

    @Inject(method = "handlePlayerListItem", at = @At("RETURN"))
    private void afterHandlePlayerListItem(S38PacketPlayerListItem packetIn, CallbackInfo ci) {
        TabList.INSTANCE.handlePlayersChanged();
    }
}
