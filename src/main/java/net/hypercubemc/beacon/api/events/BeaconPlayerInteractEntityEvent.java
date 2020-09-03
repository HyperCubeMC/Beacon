package net.hypercubemc.beacon.api.events;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerInteractEntityEvent extends BeaconEvent {
    public static void firePre(PlayerInteractEntityC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerInteractEntityEventHandlerMethodHandles, packet, player, callbackInfo);
    }
    public static void firePost(PlayerInteractEntityC2SPacket packet, ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerInteractEntityEventHandlerMethodHandles, packet, player);
    }
}
