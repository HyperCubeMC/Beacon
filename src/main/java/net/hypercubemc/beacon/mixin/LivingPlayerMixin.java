package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.api.events.BeaconPlayerDeathEvent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class LivingPlayerMixin {
    @Inject(method = "onDeath", at = @At(value = "HEAD"))
    public void prePlayerDeath(DamageSource source, CallbackInfo callbackInfo) {
        BeaconPlayerDeathEvent.firePre(source, callbackInfo);
    }
    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    public void postPlayerDeath(DamageSource source, CallbackInfo callbackInfo) {
        BeaconPlayerDeathEvent.firePost(source, callbackInfo);
    }
}
