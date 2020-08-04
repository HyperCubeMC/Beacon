package net.hypercubemc.beacon.api.events;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconLeaveEvent extends BeaconEvent {
    public static void fire(ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> playerLeaveEventHandlerMethods = BeaconEventManager.playerLeaveEventHandlerMethods;
        BeaconEventManager.fire(playerLeaveEventHandlerMethods, playerEntity, callbackInfo);
    }
}