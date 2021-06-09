package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.Mod;
import net.hypercubemc.beacon.api.events.*;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net/minecraft/server/network/ServerPlayNetworkHandler$1")
public abstract class ServerPlayNetworkHandlerMixin2 implements PlayerInteractEntityC2SPacket.Handler {
    @Shadow
    public ServerPlayNetworkHandler field_28963;

    @Shadow
    public Entity field_28962;

    @Inject(method = "interactAt(Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(value = "HEAD"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void prePlayerInteractEntity(Hand hand, Vec3d pos, CallbackInfo callbackInfo) {
        BeaconPlayerInteractEntityEvent.firePre(hand, pos, field_28962, field_28963.player, callbackInfo);
    }

    @Inject(method = "interactAt(Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler$1;processInteract(Lnet/minecraft/util/Hand;Lnet/minecraft/server/network/ServerPlayNetworkHandler$Interaction;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postPlayerInteractEntity(Hand hand, Vec3d pos, CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerInteractEntityEvent.firePost(hand, pos, field_28962, field_28963.player);
        }
    }

    @Inject(method = "attack()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void prePlayerAttackEntity(CallbackInfo callbackInfo) {
        BeaconPlayerAttackEntityEvent.firePre(field_28962, field_28963.player, callbackInfo);
    }

    @Inject(method = "attack()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postPlayerAttackEntity(CallbackInfo callbackInfo) {
        if (!callbackInfo.isCancelled()) {
            BeaconPlayerAttackEntityEvent.firePost(field_28962, field_28963.player);
        }
    }
}
