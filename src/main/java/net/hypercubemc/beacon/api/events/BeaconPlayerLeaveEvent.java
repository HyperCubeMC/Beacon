package net.hypercubemc.beacon.api.events;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerLeaveEvent extends BeaconEvent {
    public static void firePre(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerLeaveEventHandlerMethodHandles, player, callbackInfo);
    }
    public static void firePost(ServerPlayerEntity player) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerLeaveEventHandlerMethodHandles, player);
    }
}