package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerJoinEvent extends BeaconEvent {
    public static void firePre(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> prePlayerJoinEventHandlerMethods = BeaconEventManager.prePlayerJoinEventHandlerMethods;
        BeaconEventManager.fire(prePlayerJoinEventHandlerMethods, clientConnection, playerEntity, callbackInfo);
    }
    public static void firePost(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> postPlayerJoinEventHandlerMethods = BeaconEventManager.postPlayerJoinEventHandlerMethods;
        BeaconEventManager.fire(postPlayerJoinEventHandlerMethods, clientConnection, playerEntity, callbackInfo);
    }
}
