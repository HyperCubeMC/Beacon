package net.hypercubemc.beacon.api.events;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BeaconPlayerMoveEvent extends BeaconEvent {
    public static void firePre(PlayerMoveC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerMoveEventHandlerMethodHandles, packet, player, callbackInfo);
    }
    public static void firePost(PlayerMoveC2SPacket packet, ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerMoveEventHandlerMethodHandles, packet, player);
    }
}
