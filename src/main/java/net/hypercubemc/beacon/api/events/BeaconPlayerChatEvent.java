package net.hypercubemc.beacon.api.events;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerChatEvent extends BeaconEvent {
    public static void firePre(ChatMessageC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        final List<Method> prePlayerChatEventHandlerMethods = BeaconEventManager.prePlayerChatEventHandlerMethods;
        BeaconEventManager.fire(prePlayerChatEventHandlerMethods, packet, player, callbackInfo);
    }
    public static void firePost(ChatMessageC2SPacket packet, ServerPlayerEntity player) {
        final List<Method> postPlayerChatEventHandlerMethods = BeaconEventManager.postPlayerChatEventHandlerMethods;
        BeaconEventManager.fire(postPlayerChatEventHandlerMethods, packet, player);
    }
}
