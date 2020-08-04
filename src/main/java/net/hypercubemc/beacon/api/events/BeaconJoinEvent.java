package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconJoinEvent extends BeaconEvent {
    public static void fire(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> playerJoinEventHandlerMethods = BeaconEventManager.playerJoinEventHandlerMethods;
        BeaconEventManager.fire(playerJoinEventHandlerMethods, clientConnection, playerEntity, callbackInfo);
    }
}
