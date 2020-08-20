package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.api.events.BeaconPlayerDeathEvent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "onDeath",
            at = @At(value = "HEAD"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void prePlayerDeath(DamageSource source, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        BeaconPlayerDeathEvent.firePre(player, source, callbackInfo);
    }
    @Inject(method = "onDeath",
            at = @At(value = "TAIL"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void postPlayerDeath(DamageSource source, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        BeaconPlayerDeathEvent.firePost(player, source);
    }
}
