package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.api.events.BeaconPlayerAttackEntityEvent;
import net.hypercubemc.beacon.api.events.BeaconPlayerChatEvent;
import net.hypercubemc.beacon.api.events.BeaconPlayerInteractEntityEvent;
import net.hypercubemc.beacon.api.events.BeaconPlayerPlaceBlockEvent;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), cancellable = true)
    public void prePlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerInteractEntityEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    public void postPlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerInteractEntityEvent.firePost(packet, player);
        }
    }

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"), cancellable = true)
    public void prePlayerAttackEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerAttackEntityEvent.firePre(packet, callbackInfo, player);
    }

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"))
    public void postPlayerAttackEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerAttackEntityEvent.firePost(packet, player);
        }
    }

    @Inject(method = "onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;canPlayerModifyAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;)Z"), cancellable = true)
    public void prePlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerPlaceBlockEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;canPlayerModifyAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER))
    public void postPlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerPlaceBlockEvent.firePost(packet, player);
        }
    }

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"), cancellable = true)
    public void preChatMessage(ChatMessageC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerChatEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V", shift = At.Shift.AFTER))
    public void postChatMessage(ChatMessageC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerChatEvent.firePost(packet, player);
        }
    }
}
