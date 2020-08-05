package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerJoinEvent extends BeaconEvent {
    public static void firePre(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        final List<Method> prePlayerJoinEventHandlerMethods = BeaconEventManager.prePlayerJoinEventHandlerMethods;
        BeaconEventManager.fire(prePlayerJoinEventHandlerMethods, clientConnection, player, callbackInfo);
    }
    public static void firePost(ClientConnection clientConnection, ServerPlayerEntity player) {
        final List<Method> postPlayerJoinEventHandlerMethods = BeaconEventManager.postPlayerJoinEventHandlerMethods;
        BeaconEventManager.fire(postPlayerJoinEventHandlerMethods, clientConnection, player);
    }
}
