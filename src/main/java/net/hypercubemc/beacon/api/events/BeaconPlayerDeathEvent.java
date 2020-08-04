package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerDeathEvent extends BeaconEvent {
    public static void fire(DamageSource source, CallbackInfo callbackInfo) {
        final List<Method> playerDeathEventHandlerMethods = BeaconEventManager.playerDeathEventHandlerMethods;
        BeaconEventManager.fire(playerDeathEventHandlerMethods, source, callbackInfo);
    }
}
