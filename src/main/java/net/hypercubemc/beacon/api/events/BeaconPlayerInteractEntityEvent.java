package net.hypercubemc.beacon.api.events;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerInteractEntityEvent extends BeaconEvent {
    public static void firePre(PlayerInteractEntityC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        final List<Method> prePlayerInteractEntityEventHandlerMethods = BeaconEventManager.prePlayerInteractEntityEventHandlerMethods;
        BeaconEventManager.fire(prePlayerInteractEntityEventHandlerMethods, packet, player, callbackInfo);
    }
    public static void firePost(PlayerInteractEntityC2SPacket packet, ServerPlayerEntity player) {
        final List<Method> postPlayerInteractEntityEventHandlerMethods = BeaconEventManager.postPlayerInteractEntityEventHandlerMethods;
        BeaconEventManager.fire(postPlayerInteractEntityEventHandlerMethods, packet, player);
    }
}
