package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BeaconPlayerInteractEntityEvent extends BeaconEvent {
    public static void firePre(Hand hand, Vec3d pos, Entity entity, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerInteractEntityEventHandlerMethodHandles, hand, pos, entity, player, callbackInfo);
    }
    public static void firePost(Hand hand, Vec3d pos, Entity entity, ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerInteractEntityEventHandlerMethodHandles, hand, pos, entity, player);
    }
}
