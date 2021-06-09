package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.Mod;
import net.hypercubemc.beacon.api.events.*;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    private long lastKeepAliveTime;

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;disconnect" +
                    "(Lnet/minecraft/text/Text;)V",
            ordinal = 2
    ))
    private void disconnect(ServerPlayNetworkHandler handler, Text reason) {
        final long keepAliveTimeoutMs = Mod.getConfig().node("keepalive-timeout").getInt() * 1000L;

        if (Util.getMeasuringTimeMs() - lastKeepAliveTime >= keepAliveTimeoutMs) {
            handler.disconnect(reason);
        }
    }

    @Inject(method = "onPlayerInteractBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;canPlayerModifyAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void prePlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerPlaceBlockEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onPlayerInteractBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;canPlayerModifyAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postPlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerPlaceBlockEvent.firePost(packet, player);
        }
    }

    @Inject(method = "onGameMessage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;filterText(Ljava/lang/String;Ljava/util/function/Consumer;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void preChatMessage(ChatMessageC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerChatEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onGameMessage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;filterText(Ljava/lang/String;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postChatMessage(ChatMessageC2SPacket packet, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerChatEvent.firePost(packet, player);
        }
    }

    @Inject(method = "onPlayerMove",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getBoundingBox()Lnet/minecraft/util/math/Box;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void prePlayerMove(PlayerMoveC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerMoveEvent.firePre(packet, player, callbackInfo);
    }

    @Inject(method = "onPlayerMove",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerMoveEvent.firePost(packet, player);
    }

    @Inject(method = "onPlayerMove",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER, remap = false),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onPlayerFailSpeed(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (Mod.getConfig().node("enhanced-anti-cheat").getBoolean()) {
            Mod.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("[WARN] Player " + player.getName().asString() + " is faster than normal!"), MessageType.SYSTEM, Util.NIL_UUID);
        }
    }

    @Inject(method = "onPlayerMove",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.AFTER, remap = false),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onPlayerMoveWrongly(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (Mod.getConfig().node("enhanced-anti-cheat").getBoolean()) {
            Mod.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("[WARN] Player " + player.getName().asString() + " is moving wrongly!"), MessageType.SYSTEM, Util.NIL_UUID);
        }
    }
}
