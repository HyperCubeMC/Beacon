package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BeaconJoinEvent extends BeaconEvent {
    public BeaconJoinEvent(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {

    }
}
