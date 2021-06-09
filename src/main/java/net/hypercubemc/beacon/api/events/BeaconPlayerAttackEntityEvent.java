package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerAttackEntityEvent extends BeaconEvent {
    public static void firePre(Entity entity, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerAttackEntityEventHandlerMethodHandles, entity, player, callbackInfo);
    }
    public static void firePost(Entity entity, ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerAttackEntityEventHandlerMethodHandles, entity, player);
    }
}
