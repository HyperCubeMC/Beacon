package net.hypercubemc.beacon.api.events;

import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerPlaceBlockEvent extends BeaconEvent {
    public static void firePre(PlayerInteractBlockC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerPlaceBlockEventHandlerMethodHandles, packet, player, callbackInfo);
    }
    public static void firePost(PlayerInteractBlockC2SPacket packet, ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerPlaceBlockEventHandlerMethodHandles, packet, player);
    }
}
