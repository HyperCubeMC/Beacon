package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerInteractEntityEvent extends BeaconEvent {
    public static void firePre(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo, PlayerEntity player) {
        final List<Method> prePlayerInteractEntityEventHandlerMethods = BeaconEventManager.prePlayerInteractEntityEventHandlerMethods;
        BeaconEventManager.fire(prePlayerInteractEntityEventHandlerMethods, packet, callbackInfo, player);
    }
    public static void firePost(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo, PlayerEntity player) {
        final List<Method> postPlayerInteractEntityEventHandlerMethods = BeaconEventManager.postPlayerInteractEntityEventHandlerMethods;
        BeaconEventManager.fire(postPlayerInteractEntityEventHandlerMethods, packet, callbackInfo, player);
    }
}
