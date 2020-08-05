package net.hypercubemc.beacon.api.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerPlaceBlockEvent extends BeaconEvent {
    public static void firePre(PlayerInteractBlockC2SPacket packet, PlayerEntity player, CallbackInfo callbackInfo) {
        final List<Method> prePlayerPlaceBlockEventHandlerMethods = BeaconEventManager.prePlayerPlaceBlockEventHandlerMethods;
        BeaconEventManager.fire(prePlayerPlaceBlockEventHandlerMethods, packet, player, callbackInfo);
    }
    public static void firePost(PlayerInteractBlockC2SPacket packet, PlayerEntity player) {
        final List<Method> postPlayerPlaceBlockEventHandlerMethods = BeaconEventManager.postPlayerPlaceBlockEventHandlerMethods;
        BeaconEventManager.fire(postPlayerPlaceBlockEventHandlerMethods, packet, player);
    }
}
