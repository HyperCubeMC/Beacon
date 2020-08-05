package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerAttackEntityEvent extends BeaconEvent {
    public static void firePre(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo, PlayerEntity player) {
        final List<Method> prePlayerBreakBlockEventHandlerMethods = BeaconEventManager.prePlayerAttackEntityEventHandlerMethods;
        BeaconEventManager.fire(prePlayerBreakBlockEventHandlerMethods, packet, callbackInfo, player);
    }
    public static void firePost(PlayerInteractEntityC2SPacket packet, PlayerEntity player) {
        final List<Method> postPlayerBreakBlockEventHandlerMethods = BeaconEventManager.postPlayerAttackEntityEventHandlerMethods;
        BeaconEventManager.fire(postPlayerBreakBlockEventHandlerMethods, packet, player);
    }
}
