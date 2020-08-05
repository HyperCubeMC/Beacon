package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerDeathEvent extends BeaconEvent {
    public static void firePre(PlayerEntity player, DamageSource source, CallbackInfo callbackInfo) {
        final List<Method> prePlayerDeathEventHandlerMethods = BeaconEventManager.prePlayerDeathEventHandlerMethods;
        BeaconEventManager.fire(prePlayerDeathEventHandlerMethods, source, callbackInfo);
    }
    public static void firePost(PlayerEntity player, DamageSource source) {
        final List<Method> postPlayerDeathEventHandlerMethods = BeaconEventManager.postPlayerDeathEventHandlerMethods;
        BeaconEventManager.fire(postPlayerDeathEventHandlerMethods, source);
    }
}
