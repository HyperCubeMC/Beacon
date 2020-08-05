package net.hypercubemc.beacon.api.chat;

import net.hypercubemc.beacon.Mod;
import net.hypercubemc.beacon.api.events.BeaconEventFireStage;
import net.hypercubemc.beacon.api.events.BeaconEventHandler;
import net.hypercubemc.beacon.api.events.BeaconEventListener;
import net.hypercubemc.beacon.api.events.BeaconPlayerChatEvent;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BeaconChatManagerEventListener implements BeaconEventListener {
    @BeaconEventHandler(value = BeaconPlayerChatEvent.class, fireStage = BeaconEventFireStage.PRE)
    public static void onChatMessage(ChatMessageC2SPacket packet, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconChatState chatState = BeaconChatManager.getInstance().getChatState();
        if (chatState == BeaconChatState.MUTED) {
            callbackInfo.cancel();
        }
        else if (chatState == BeaconChatState.MUTED_WITH_OP_BYPASS) {
            boolean isOperator = Mod.getMinecraftServer().getPlayerManager().isOperator(player.getGameProfile());
            if (!isOperator) {
                callbackInfo.cancel();
            }
        }
    }
}
