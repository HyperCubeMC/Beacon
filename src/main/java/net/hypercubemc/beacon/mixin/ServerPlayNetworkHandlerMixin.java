package net.hypercubemc.beacon.mixin;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.hypercubemc.beacon.api.events.BeaconPlayerInteractEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), cancellable = true)
    public void prePlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerInteractEntityEvent.firePre(packet, callbackInfo, player);
    }
    @Inject(method = "onPlayerInteractEntity", at = @At(value = "TAIL"))
    public void postPlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo callbackInfo) {
        BeaconPlayerInteractEntityEvent.firePost(packet, player);
    }
}
